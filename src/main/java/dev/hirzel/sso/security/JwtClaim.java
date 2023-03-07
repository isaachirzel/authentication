package dev.hirzel.sso.security;

public enum JwtClaim
{
	IssuedAt("iat"),
	Subject("sub");

	private final String key;

	JwtClaim(String key)
	{
		this.key = key;
	}

	@Override
	public String toString()
	{
		return this.key;
	}
}
