package dev.hirzel.authentication.service;

import dev.hirzel.authentication.dto.AuthenticationResult;
import dev.hirzel.authentication.dto.RegistrationInfo;
import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.exception.NullArgumentException;
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

	public AuthenticationResult registerUser(RegistrationInfo info)
	{
		// TODO: Validate RegistrationDto
		if (info.getUsername() == null)
			throw new NullArgumentException("username");

		if (info.getPassword() == null)
			throw new NullArgumentException("password");

		if (info.getFirstName() == null)
			throw new NullArgumentException("firstName");

		if (info.getLastName() == null)
			throw new NullArgumentException("lastName");

		var passwordHash = authenticationService.hashPassword(info.getPassword());
		var user = new User(info.getUsername(), passwordHash, info.getFirstName(), info.getLastName());
		var savedUser = userService.createUser(user);
		var session = sessionService.createSession(savedUser);
		var result = new AuthenticationResult(user, session);

		return result;
	}
}
