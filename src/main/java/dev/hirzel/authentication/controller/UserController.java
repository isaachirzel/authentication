package dev.hirzel.authentication.controller;

import dev.hirzel.authentication.entity.User;
import dev.hirzel.authentication.service.AuthenticationService;
import dev.hirzel.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable long id)
    {
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getUsers()
    {
        return userService.getAllUsers();
    }
}
