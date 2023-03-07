package dev.hirzel.authentication.controller;

import dev.hirzel.authentication.dto.RegistrationDto;
import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController
{
	@Autowired
	RegistrationService registrationService;

	@PostMapping
	public User registerUser(@RequestBody RegistrationDto dto)
	{
		var user = registrationService.registerUser(dto);

		return user;
	}
}
