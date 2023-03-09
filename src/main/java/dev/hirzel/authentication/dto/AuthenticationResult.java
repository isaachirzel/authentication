package dev.hirzel.authentication.dto;

import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.security.Session;

public class AuthenticationResult
{
	private User user;
	private String token;

	public AuthenticationResult(User user, Session session)
	{
		this.user = user;
		this.token = session.getToken();
	}
}
