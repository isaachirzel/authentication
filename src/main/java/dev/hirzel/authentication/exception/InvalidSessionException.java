package dev.hirzel.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.server.ResponseStatusException;

public class InvalidSessionException extends ResponseStatusException
{
	public InvalidSessionException()
	{
		super(HttpStatus.UNAUTHORIZED, "Invalid session. Nice try lmao.");
	}
}
