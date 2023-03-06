package dev.hirzel.sso.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.hirzel.sso.configuration.ApplicationConfiguration;
import dev.hirzel.sso.dto.UserDto;
import dev.hirzel.sso.entity.User;
import dev.hirzel.sso.exception.AuthenticationException;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthenticationService {
	private static final long MAX_JWT_AGE = 60 * 15; // 15 minutes
	private static final String TOKEN_COOKIE_NAME = "sso_auth_token";
	@Autowired
	UserService userService;

	public String getTokenForUser(UserDto dto) throws Exception {
		var user = getAuthenticatedUser(dto.username, dto.password);
		var token = createUserToken(user.getId());

		return token;
	}

	private User getAuthenticatedUser(String username, String password) throws Exception {
		var optionalUser = userService.findUser(username);

		if (optionalUser.isEmpty())
			throw new AuthenticationException();

		var user = optionalUser.get();
		var encoder = new BCryptPasswordEncoder();
		var passwordHash = encoder.encode(password).getBytes(StandardCharsets.UTF_8);

		if (!Arrays.equals(passwordHash, user.getPasswordHash()))
			throw new AuthenticationException();

		return user;
	}

	public String createUserToken(long userId) throws Exception
	{
		var algorithm = getEncryptionAlgorithm();
		var token = JWT.create()
				.withClaim("sub", userId)
				.withClaim("iat", Instant.now().getEpochSecond())
				.sign(algorithm);

		return token;
	}

	public String refreshToken(String token) throws Exception {
		var algorithm = getEncryptionAlgorithm();
		var verifier = JWT
				.require(algorithm)
				.build();
		var jwt = verifier.verify(token);
		var userIdClaim = jwt.getClaim("userId");
		var userId = userIdClaim.asLong();
		var refreshedToken = createUserToken(userId);

		return refreshedToken;
	}

	public Algorithm getEncryptionAlgorithm() throws Exception
	{
		var appConfig = ApplicationConfiguration.getInstance();
		var algorithm = Algorithm.HMAC256(appConfig.getJwtSecret());

		return algorithm;
	}

	public Cookie getTokenCookie(String token) {
		var cookie = new Cookie(TOKEN_COOKIE_NAME, token);

		cookie.setHttpOnly(true);
		cookie.setSecure(true);

		return cookie;
	}

	public Cookie findTokenCookie(Cookie[] cookies) {
		for (var cookie : cookies) {
			if (cookie.getName().equals(TOKEN_COOKIE_NAME))
				return cookie;
		}

		return null;
	}

	private boolean isJwtValid(DecodedJWT jwt)
	{
		var iat = jwt.getClaim("iat");
		var createdTime = iat.asLong();
		var currentTime = Instant.now().getEpochSecond();
		var age = currentTime - createdTime;
		var isValid = age <= MAX_JWT_AGE;

		return isValid;
	}
}
