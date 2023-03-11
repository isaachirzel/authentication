package dev.hirzel.authentication.service;

import dev.hirzel.authentication.dto.UserDto;
import dev.hirzel.authentication.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificationService
{
	@Autowired
	SessionService sessionService;
	@Autowired
	UserService userService;

	public UserDto verify(String bearerToken) throws UnauthorizedException
	{
		var sessionToken = getSessionToken(bearerToken);
		var session = sessionService.getSession(sessionToken);

		if (session.isExpired())
			throw new UnauthorizedException("Session has timed out.");

		var userId = session.getUserId();
		var user = userService.getUser(userId);
		var dto = new UserDto(user);

		session.refresh();

		return dto;
	}

	private String getSessionToken(String bearerToken)
	{
		if (!bearerToken.startsWith("Bearer "))
			throw new UnauthorizedException("Authorization header is not a bearer token.");

		var tokenParts = bearerToken.split(" ");

		if (tokenParts.length != 2)
			throw new UnauthorizedException("Bearer token is invalid.");

		var sessionToken = tokenParts[1].trim();

		try
		{
			var uuid = UUID.fromString(sessionToken);

			return uuid.toString();
		}
		catch (Exception e)
		{
			throw new UnauthorizedException("Session token is invalid.");
		}
	}
}
