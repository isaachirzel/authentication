package dev.hirzel.authentication.dto;

public class AuthenticationInfo
{
	private String token;
	private String username;
	private String password;

	public String getToken()
	{
		return token;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}
}
