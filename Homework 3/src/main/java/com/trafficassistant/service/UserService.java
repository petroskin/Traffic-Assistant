package com.trafficassistant.service;

import com.trafficassistant.model.User;
import com.trafficassistant.repository.UserRepository;
import com.trafficassistant.repository.jpa.JpaEventRepository;
import com.trafficassistant.repository.jpa.JpaUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final JpaUserRepository userRepository;

    public UserService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String fullName, String username, String email, String password)
    {
        this.userRepository.save(new User(fullName, username, email, password));
    }

    public List<User> getUsers()
    {
        return this.userRepository.findAll();
    }
    public User getByUsername(String username)
    {
        return userRepository.findById(username).orElse(null);
    }
}
