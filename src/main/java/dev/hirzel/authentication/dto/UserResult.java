package dev.hirzel.authentication.dto;

import dev.hirzel.authentication.entity.User;

public class UserResult
{
	private long id;
	private String username;
	private String firstName;
	private String lastName;

	public UserResult(User user)
	{
		this.id = user.getId();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
	}

	public long getId()
	{
		return id;
	}

	public String getUsername()
	{
		return username;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}
}
