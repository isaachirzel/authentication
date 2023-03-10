package dev.hirzel.authentication.service;

import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.exception.UnauthorizedException;
import dev.hirzel.authentication.security.Session;
import jakarta.annotation.Nonnull;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Hashtable;

@Service
public class SessionService
{
	private static final Hashtable<String, Session> sessions = new Hashtable<>();

	/**
	 * @returns Current session or null is not found
	 */
	public @Nonnull Session getSession(String token) throws UnauthorizedException
	{
		var session = sessions.get(token);

		if (session == null)
			throw new UnauthorizedException("Token is invalid.");

		return session;
	}

	public Session createSession(User user)
	{
		for (var sessionEntries : sessions.entrySet()) {
			var session = sessionEntries.getValue();

			if (session.getUserId() == user.getId())
			{
				session.refresh();

				return session;
			}
		}

		var session = new Session(user);

		sessions.put(session.getToken(), session);

		return session;
	}
}
