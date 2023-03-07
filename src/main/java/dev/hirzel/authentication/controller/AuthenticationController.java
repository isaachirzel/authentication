package dev.hirzel.authentication.controller;

import dev.hirzel.authentication.dto.AuthenticationDto;
import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.repository.UserRepository;
import dev.hirzel.authentication.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthenticationService authenticationService;

	@PostMapping
	public User Index(HttpServletResponse response, @RequestBody AuthenticationDto dto) throws Exception
	{
		var user = authenticationService.getAuthenticatedUser(dto);
		var token = authenticationService.createAuthenticationToken(user);
		var tokenCookie = authenticationService.createTokenCookie(token);

		response.addCookie(tokenCookie);

		return user;
	}

	@GetMapping("redirect")
	public void Redirect(HttpServletResponse response) throws Exception
	{
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.sendRedirect("https://google.com");
	}
}
