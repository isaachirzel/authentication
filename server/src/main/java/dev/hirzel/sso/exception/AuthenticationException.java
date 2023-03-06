package dev.hirzel.sso.exception;

public class AuthenticationException extends Exception {
	public AuthenticationException() {
		super("Invalid username or password.");
	}
}
