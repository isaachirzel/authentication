package dev.hirzel.authentication.dto;

public class RegistrationInfo {
	public String username;
	public String password;
	public String firstName;
	public String lastName;

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
