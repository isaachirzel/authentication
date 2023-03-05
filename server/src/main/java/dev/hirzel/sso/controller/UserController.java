package dev.hirzel.sso.controller;

import dev.hirzel.sso.entity.User;
import dev.hirzel.sso.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable long id)
    {
        return userService.findById(id);
    }

    @GetMapping
    public List<User> getUsers()
    {
        return userService.findAll();
    }
}
