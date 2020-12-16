package com.trafficassistant.service;

import com.trafficassistant.model.User;
import com.trafficassistant.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String fullName, String username, String email, String password)
    {
        this.userRepository.addUser(fullName, username, email, password);
    }

    public List<User> getUsers()
    {
        return this.userRepository.getUsers();
    }
}
