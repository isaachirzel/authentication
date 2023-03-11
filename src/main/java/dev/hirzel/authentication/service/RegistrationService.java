package dev.hirzel.authentication.service;

import dev.hirzel.authentication.dto.RegistrationDto;
import dev.hirzel.authentication.dto.UserDto;
import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.exception.BadRequestException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService
{
	@Autowired
	UserService userService;
	@Autowired
	AuthenticationService authenticationService;
	@Autowired
	SessionService sessionService;

	public UserDto register(RegistrationDto info, HttpServletResponse response)
	{
		// TODO: Validate RegistrationDto
		if (info.getUsername() == null)
			throw new BadRequestException("Username is required.");

		if (info.getPassword() == null)
			throw new BadRequestException("Password is required.");

		if (info.getFirstName() == null)
			throw new BadRequestException("FirstName is required.");

		if (info.getLastName() == null)
			throw new BadRequestException("LastName is required.");

		// TODO: Check for duplicates

		var passwordHash = authenticationService.hashPassword(info.getPassword());
		var user = new User(info.getUsername(), passwordHash, info.getFirstName(), info.getLastName());
		var savedUser = userService.createUser(user);
		var session = sessionService.createSession(savedUser);
		var cookie = sessionService.createSessionCookie(session);
		var result = new UserDto(savedUser);

		response.addCookie(cookie);

		return result;
	}
}
