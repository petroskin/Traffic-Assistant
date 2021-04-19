package com.trafficassistant.userservice.service;

import com.trafficassistant.userservice.model.User;

public interface UserService
{
    public String addUser(User user);

    public User getByUsername(String username);

    public User logIn(String info);

    public User register(User user);
}
