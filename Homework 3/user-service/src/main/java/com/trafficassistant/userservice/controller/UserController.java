package com.trafficassistant.userservice.controller;

import com.trafficassistant.userservice.model.User;
import com.trafficassistant.userservice.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users-rest")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user)
    {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody String info)
    {
        return userService.logIn(info);
    }
}
