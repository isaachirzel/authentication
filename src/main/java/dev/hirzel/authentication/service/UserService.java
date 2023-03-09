package dev.hirzel.authentication.service;

import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.exception.ConflictException;
import dev.hirzel.authentication.exception.NotFoundException;
import dev.hirzel.authentication.repository.UserRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public @Nullable User findUser(String username) {
		var users = userRepository.findByUsername(username);
		var optionalUser =  users.stream().findFirst();
		var user = !optionalUser.isEmpty()
			? optionalUser.get()
			: null;

		return user;
	}

	public @Nonnull User getUser(long id) {
		var user = userRepository.findById(id);

		if (user.isEmpty())
			throw new NotFoundException(User.class, id);

		return user.get();
	}

	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}

	public User createUser(User user)
	{
		var userWithSameUsername = findUser(user.getUsername());

		if (userWithSameUsername != null)
			throw new ConflictException("Username `" + user.getUsername() + "` is already taken.");

		var savedUser = userRepository.save(user);

		return savedUser;
	}
}
