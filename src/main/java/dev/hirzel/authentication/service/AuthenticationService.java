package dev.hirzel.authentication.service;

import dev.hirzel.authentication.dto.AuthenticationInfo;
import dev.hirzel.authentication.dto.AuthenticationResult;
import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.exception.AuthenticationException;
import dev.hirzel.authentication.exception.InvalidSessionException;
import dev.hirzel.authentication.exception.NullArgumentException;
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
				throw new InvalidSessionException();
		}
		else if (!user.getUsername().equals(info.getUsername()) || !isPasswordCorrect(user, info.getPassword()))
		{
			throw new AuthenticationException();
		}

		session.refresh();

		var result = new AuthenticationResult(user, session);

		return result;
	}

	public User getAuthenticatedUser(AuthenticationInfo info) throws AuthenticationException
	{
		if (info.getUsername() == null || info.getPassword() == null)
			throw new AuthenticationException();

		var user = userService.findUser(info.getUsername());

		if (user == null || !isPasswordCorrect(user, info.getPassword()))
			throw new AuthenticationException();

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
