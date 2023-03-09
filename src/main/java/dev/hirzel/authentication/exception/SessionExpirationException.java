package dev.hirzel.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SessionExpirationException extends ResponseStatusException
{
	public SessionExpirationException()
	{
		super(HttpStatus.UNAUTHORIZED, "The session has expired and re-authentication is required.");
	}
}
