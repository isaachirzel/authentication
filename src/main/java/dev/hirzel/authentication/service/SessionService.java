package dev.hirzel.authentication.service;

import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.security.Session;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Hashtable;

@Service
public class SessionService
{
	private static final Hashtable<String, Session> sessions = new Hashtable<>();

	/**
	 * @returns Current session or null is not found
	 */
	public Session getSession(String token)
	{
		return sessions.get(token);
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
