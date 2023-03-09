package dev.hirzel.authentication.service;

import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.repository.UserRepository;
import jakarta.annotation.Nullable;
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

	public @Nullable User findUser(String username) {
		var users = userRepository.findByUsername(username);
		var optionalUser =  users.stream().findFirst();
		var user = optionalUser.isEmpty()
			? null
			: optionalUser.get();

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
