package dev.hirzel.sso.controller;

import dev.hirzel.sso.dto.UserDto;
import dev.hirzel.sso.entity.User;
import dev.hirzel.sso.service.AuthenticationService;
import dev.hirzel.sso.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    public User registerUser(HttpServletResponse response, @RequestBody UserDto dto) throws Exception {
        var user = userService.createUser(dto);
        var token = authenticationService.createUserToken(dto);
        var cookie = authenticationService.getTokenCookie(token);

        response.addCookie(cookie);

        return user;
    }
}
