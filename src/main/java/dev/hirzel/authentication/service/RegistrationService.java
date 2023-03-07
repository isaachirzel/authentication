package dev.hirzel.authentication.service;

import dev.hirzel.authentication.dto.RegistrationDto;
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

	public User registerUser(RegistrationDto dto)
	{
		// TODO: Validate RegistrationDto
		var passwordHash = authenticationService.hashPassword(dto.password);
		var user = new User(dto.username, passwordHash, dto.firstName, dto.lastName);
		var savedUser = userService.createUser(user);

		return savedUser;
	}
}
