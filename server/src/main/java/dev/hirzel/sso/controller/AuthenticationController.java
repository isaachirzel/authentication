package dev.hirzel.sso.controller;

import dev.hirzel.sso.dto.UserDto;
import dev.hirzel.sso.repository.UserRepository;
import dev.hirzel.sso.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthenticationService authenticationService;

	@PostMapping
	public void Index(HttpServletRequest request, HttpServletResponse response, @RequestBody UserDto dto) throws Exception {
		var tokenCookie = authenticationService.findTokenCookie(request.getCookies());
		var token = tokenCookie == null
				? authenticationService.getTokenForUser(dto)
				: authenticationService.refreshToken(tokenCookie.getValue());

		tokenCookie = authenticationService.getTokenCookie(token);
		response.addCookie(tokenCookie);
	}
}
