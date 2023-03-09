package dev.hirzel.authentication.dto;

import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.security.Session;

public class AuthenticationResult
{
	private String token;
	private UserResult user;

	public AuthenticationResult(User user, Session session)
	{
		this.token = session.getToken();
		this.user = new UserResult(user);
	}

	public UserResult getUser() {
		return user;
	}

	public String getToken() {
		return token;
	}
}
