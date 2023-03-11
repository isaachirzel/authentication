package dev.hirzel.authentication.service;

import dev.hirzel.authentication.dto.UserDto;
import dev.hirzel.authentication.dto.VerificationDto;
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

	public UserDto verify(VerificationDto info) throws UnauthorizedException
	{
		var session = sessionService.getSession(info.getToken());

		if (session.isExpired())
			throw new UnauthorizedException("Session has timed out.");

		var userId = session.getUserId();
		var user = userService.getUser(userId);
		var result = new UserDto(user);

		session.refresh();

		return result;
	}
}
