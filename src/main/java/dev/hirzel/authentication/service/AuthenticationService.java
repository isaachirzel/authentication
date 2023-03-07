package dev.hirzel.authentication.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.hirzel.authentication.configuration.ApplicationConfiguration;
import dev.hirzel.authentication.dto.AuthenticationDto;
import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.exception.AuthenticationException;
import dev.hirzel.authentication.exception.NullArgumentException;
import dev.hirzel.authentication.security.JwtClaim;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthenticationService
{
	private static final long MAX_JWT_AGE = 60 * 15; // 15 minutes
	private static final String TOKEN_COOKIE_NAME = "hirzel_auth_token";
	@Autowired
	UserService userService;

	public String createAuthenticationToken(User user) throws Exception
	{
		var algorithm = getEncryptionAlgorithm();
		var token = JWT.create()
				.withClaim(JwtClaim.Subject.toString(), user.getId())
				.withClaim(JwtClaim.IssuedAt.toString(), Instant.now().getEpochSecond())
				.sign(algorithm);

		return token;
	}

	public User getUserFromToken(String token) throws Exception
	{
		var jwt = decodeToken(token);
		var userIdClaim = jwt.getClaim(JwtClaim.Subject.toString());
		var userId = userIdClaim.asLong();
		var user = userService.getUser(userId);

		return user;
	}

	public User getAuthenticatedUser(AuthenticationDto dto) throws Exception
	{
		if (dto.token != null)
			return getUserFromToken(dto.token);

		if (dto.username == null)
			throw new NullArgumentException("username");

		if (dto.password == null)
			throw new NullArgumentException("password");

		var optionalUser = userService.findUser(dto.username);

		if (optionalUser.isEmpty())
			throw new AuthenticationException();

		var user = optionalUser.get();

		if (!isPasswordCorrect(user, dto.password))
			throw new AuthenticationException();

		return user;
	}

	private DecodedJWT decodeToken(String token) throws Exception
	{
		var algorithm = getEncryptionAlgorithm();
		var verifier = JWT
				.require(algorithm)
				.build();
		var jwt = verifier.verify(token);

		return jwt;
	}

	public String hashPassword(String password)
	{
		var encoder = new BCryptPasswordEncoder();
		var passwordHash = encoder.encode(password);

		return passwordHash;
	}

	public boolean isPasswordCorrect(User user, String password)
	{
		var encoder = new BCryptPasswordEncoder();
		var isCorrect = encoder.matches(password, user.getPasswordHash());

		return isCorrect;
	}

	public Algorithm getEncryptionAlgorithm() throws Exception
	{
		var appConfig = ApplicationConfiguration.getInstance();
		var algorithm = Algorithm.HMAC256(appConfig.getJwtSecret());

		return algorithm;
	}

	public Cookie createTokenCookie(String token) {
		var cookie = new Cookie(TOKEN_COOKIE_NAME, token);

		cookie.setHttpOnly(true);
		cookie.setSecure(true);
//		cookie.setPath()

		return cookie;
	}

	private boolean isJwtValid(DecodedJWT jwt)
	{
		var iat = jwt.getClaim(JwtClaim.IssuedAt.toString());
		var createdTime = iat.asLong();
		var currentTime = Instant.now().getEpochSecond();
		var age = currentTime - createdTime;
		var isValid = age <= MAX_JWT_AGE;

		return isValid;
	}
}
