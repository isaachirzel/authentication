package dev.hirzel.authentication.controller;

import dev.hirzel.authentication.dto.AuthenticationDto;
import dev.hirzel.authentication.dto.RegistrationDto;
import dev.hirzel.authentication.dto.UserDto;
import dev.hirzel.authentication.service.AuthenticationService;
import dev.hirzel.authentication.service.RegistrationService;
import dev.hirzel.authentication.service.VerificationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController
{
	@Autowired
	AuthenticationService authenticationService;
	@Autowired
	RegistrationService registrationService;
	@Autowired
	VerificationService verificationService;

	@PostMapping("authenticate")
	public UserDto authenticate(HttpServletResponse response, @RequestBody AuthenticationDto dto) throws Exception
	{
		return authenticationService.authenticate(dto, response);
	}

	@PostMapping("verify")
	public UserDto verify(@RequestHeader("Authorization") String token)
	{
		return verificationService.verify(token);
	}

	@PostMapping("register")
	public UserDto register(HttpServletResponse response, @RequestBody RegistrationDto info)
	{
		return registrationService.register(info, response);
	}
}
