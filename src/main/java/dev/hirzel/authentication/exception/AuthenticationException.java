package dev.hirzel.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthenticationException extends ResponseStatusException {
	public AuthenticationException() {
		super(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
	}

}
