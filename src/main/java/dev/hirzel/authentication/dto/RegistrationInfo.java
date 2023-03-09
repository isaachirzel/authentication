package dev.hirzel.authentication.dto;

public class RegistrationInfo {
	private String username;
	private String password;
	private String firstName;
	private String lastName;

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
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
