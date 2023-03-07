package dev.hirzel.sso.exception;

public class NullArgumentException extends Exception
{
	public NullArgumentException(String argumentName)
	{
		super("Argument `" + argumentName +  "` must not be null.");
	}
}
