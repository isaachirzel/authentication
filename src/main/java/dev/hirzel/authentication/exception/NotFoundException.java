package dev.hirzel.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException
{
	public NotFoundException(Class entityClass, Object id)
	{
		super(HttpStatus.NOT_FOUND, "No " + entityClass.getSimpleName() + " exists with ID " + id + ".");
	}
}
