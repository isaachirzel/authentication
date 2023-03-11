package dev.hirzel.authentication.security;

import dev.hirzel.authentication.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.UUID;

public class Session
{
	private static final long SESSION_MAX_AGE = 1440; // 24 minutes

	private UUID id;
	private long expirationTime;
	private long creationTime;
	private long userId;

	public Session(User user)
	{
		this.id = UUID.randomUUID();
		this.userId = user.getId();

		refresh();
	}

	public void refresh()
	{
		this.creationTime = Instant.now().getEpochSecond();
		this.expirationTime = this.creationTime + SESSION_MAX_AGE;
	}

	public UUID getId()
	{
		return this.id;
	}

	public long getCreatedTime()
	{
		return this.creationTime;
	}

	public long getExpirationTime()
	{
		return this.expirationTime;
	}

	public long getUserId()
	{
		return this.userId;
	}

	public String getToken()
	{
		// TODO: Encryption
		return this.id.toString();
	}

	public boolean isValid()
	{
		return this.expirationTime > Instant.now().getEpochSecond();
	}

	public boolean isExpired()
	{
		return !isValid();
	}
}
