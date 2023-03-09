package dev.hirzel.authentication.controller;

import dev.hirzel.authentication.dto.AuthenticationInfo;
import dev.hirzel.authentication.dto.AuthenticationResult;
import dev.hirzel.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController
{
	@Autowired
	AuthenticationService authenticationService;

	@PostMapping
	public AuthenticationResult Index(@RequestBody AuthenticationInfo info) throws Exception
	{
		return authenticationService.authenticate(info);
	}
}
