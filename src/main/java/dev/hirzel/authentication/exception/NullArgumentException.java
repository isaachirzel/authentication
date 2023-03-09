package dev.hirzel.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NullArgumentException extends ResponseStatusException
{
	public NullArgumentException(String argumentName)
	{
		super(HttpStatus.BAD_REQUEST, "Argument `" + argumentName +  "` must not be null.");
	}
}
