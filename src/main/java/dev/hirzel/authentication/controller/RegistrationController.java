package dev.hirzel.authentication.controller;

import dev.hirzel.authentication.dto.AuthenticationResult;
import dev.hirzel.authentication.dto.RegistrationInfo;
import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController
{
	@Autowired
	RegistrationService registrationService;

	@PostMapping
	public AuthenticationResult registerUser(@RequestBody RegistrationInfo info)
	{
		return registrationService.registerUser(info);
	}
}
