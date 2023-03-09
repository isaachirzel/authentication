package dev.hirzel.authentication.controller;

import dev.hirzel.authentication.dto.AuthenticationInfo;
import dev.hirzel.authentication.dto.AuthenticationResult;
import dev.hirzel.authentication.exception.SessionExpirationException;
import dev.hirzel.authentication.service.AuthenticationService;
import dev.hirzel.authentication.service.SessionService;
import dev.hirzel.authentication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController
{
	@Autowired
	AuthenticationService authenticationService;
	@Autowired
	SessionService sessionService;
	@Autowired
	UserService userService;

	@PostMapping
	public AuthenticationResult Index(@RequestBody AuthenticationInfo info) throws Exception
	{
		return authenticationService.authenticate(info);
	}
}
