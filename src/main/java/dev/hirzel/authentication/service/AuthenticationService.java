package dev.hirzel.authentication.service;

import dev.hirzel.authentication.dto.AuthenticationInfo;
import dev.hirzel.authentication.dto.AuthenticationResult;
import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.exception.UnauthorizedException;
import dev.hirzel.authentication.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService
{
	@Autowired
	UserService userService;
	@Autowired
	SessionService sessionService;

	public AuthenticationResult authenticate(AuthenticationInfo info) throws Exception
	{
		if (info.getToken() == null)
		{
			var user = getAuthenticatedUser(info);
			var session = sessionService.createSession(user);
			var result = new AuthenticationResult(user, session);

			return result;
		}

		var session = sessionService.getSession(info.getToken());
		var user = userService.getUser(session.getUserId());

		if (session.isValid())
		{
			if (session.getUserId() != user.getId())
				throw new UnauthorizedException("Session user does not match request user.");
		}
		else if (!user.getUsername().equals(info.getUsername()))
		{
			throw new UnauthorizedException("Username is incorrect.");
		} else if (!isPasswordCorrect(user, info.getPassword()))
		{
			throw new UnauthorizedException("Password is incorrect.");
		}

		session.refresh();

		var result = new AuthenticationResult(user, session);

		return result;
	}

	public User getAuthenticatedUser(AuthenticationInfo info) throws UnauthorizedException
	{
		if (info.getUsername() == null)
			throw new BadRequestException("Username is required.");

		if (info.getPassword() == null)
			throw new BadRequestException("Password is required.");

		var user = userService.findUser(info.getUsername());

		if (user == null)
			throw new UnauthorizedException("No user with username `" + info.getUsername()  + "` exists.");

		if (!isPasswordCorrect(user, info.getPassword()))
			throw new UnauthorizedException("Password is incorrect.");

		return user;
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
}
