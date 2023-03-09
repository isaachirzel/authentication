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
	private static final String COOKIE_NAME = "hirzel_session_token";
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

	private Cookie findSessionCookie(HttpServletRequest request)
	{
		for (var cookie : request.getCookies())
		{
			if (cookie.getName().equals(COOKIE_NAME))
				return cookie;
		}

		return null;
	}

	public Cookie createSessionCookie(Session session)
	{
		var cookie = new Cookie(COOKIE_NAME, session.getToken());

		cookie.setHttpOnly(true);
		cookie.setSecure(true);

		return cookie;
	}
}
