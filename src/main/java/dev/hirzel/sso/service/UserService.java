package dev.hirzel.sso.service;

import dev.hirzel.sso.entity.User;
import dev.hirzel.sso.repository.UserRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public boolean authenticateUser(String username, String password) {
		var encoder = new BCryptPasswordEncoder();
		var hash = encoder.encode(password);
		var user = userRepository.findByUsername(username);
		throw new NotYetImplementedException();
	}

	public User getUser(String username) {
		var users = userRepository.findByUsername(username);

		if (users.isEmpty())
			throw new NoSuchElementException("No user with username '" + username + "' exists.");

		return users.get(0);
	}

	public Optional<User> findUser(String username) {
		var users = userRepository.findByUsername(username);
		var user =  users.stream().findFirst();

		return user;
	}

	public User getUser(long id) {
		var user = userRepository.findById(id);

		if (user.isEmpty())
			throw new NoSuchElementException("No user with ID " + id + " exists.");

		return user.get();
	}

	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}

	public User createUser(User user)
	{
		var savedUser = userRepository.save(user);

		return savedUser;
	}
}
