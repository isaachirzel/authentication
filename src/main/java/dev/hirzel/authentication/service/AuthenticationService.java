package dev.hirzel.authentication.service;

import dev.hirzel.authentication.dto.AuthenticationDto;
import dev.hirzel.authentication.dto.UserDto;
import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.exception.UnauthorizedException;
import dev.hirzel.authentication.exception.BadRequestException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService
{
	@Autowired
	UserService userService;
	@Autowired
	SessionService sessionService;

	public UserDto authenticate(AuthenticationDto info, HttpServletResponse response) throws Exception
	{
		var user = getAuthenticatedUser(info);
		var session = sessionService.createSession(user);
		var cookie = sessionService.createSessionCookie(session);
		var dto = new UserDto(user);

		response.addCookie(cookie);

		return dto;
	}

	public User getAuthenticatedUser(AuthenticationDto info) throws UnauthorizedException
	{
		if (info.getUsername() == null)
			throw new BadRequestException("Username is required.");

		if (info.getPassword() == null)
			throw new BadRequestException("Password is required.");

		var user = userService.findUser(info.getUsername());

		if (user == null)
			throw new UnauthorizedException("No user with username `" + info.getUsername()  + "` exists.");

		if (!isPasswordCorrect(user, info.getPassword()))
			throw new UnauthorizedException("Password is incorrect.");

		return user;
	}

	public String hashPassword(String password)
	{
		var encoder = new BCryptPasswordEncoder();
		var passwordHash = encoder.encode(password);

		return passwordHash;
	}

	public boolean isPasswordCorrect(User user, String password)
	{
		var encoder = new BCryptPasswordEncoder();
		var isCorrect = encoder.matches(password, user.getPasswordHash());

		return isCorrect;
	}
}
