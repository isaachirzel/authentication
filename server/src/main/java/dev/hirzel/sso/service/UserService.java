package dev.hirzel.sso.service;

import dev.hirzel.sso.entity.User;
import dev.hirzel.sso.repository.UserRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	public boolean authenticateUser(String username, String password) {
		var encoder = new BCryptPasswordEncoder();
		var hash = encoder.encode(password);
		var user = userRepository.findByUsername(username);
		throw new NotYetImplementedException();
	}

	public User getUser(String username) {
		var users = userRepository.findByUsername(username);
		

		return user;
	}
}
