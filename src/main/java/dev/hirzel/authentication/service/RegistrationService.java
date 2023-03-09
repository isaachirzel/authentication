package dev.hirzel.authentication.service;

import dev.hirzel.authentication.dto.AuthenticationResult;
import dev.hirzel.authentication.dto.RegistrationInfo;
import dev.hirzel.authentication.entity.User;
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
		var passwordHash = authenticationService.hashPassword(info.password);
		var user = new User(info.username, passwordHash, info.firstName, info.lastName);
		var savedUser = userService.createUser(user);
		var session = sessionService.createSession(savedUser);
		var result = new AuthenticationResult(user, session);

		return result;
	}
}
