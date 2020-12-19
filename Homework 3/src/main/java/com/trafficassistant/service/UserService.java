package com.trafficassistant.service;

import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.InvalidCharacterInUsernameException;
import com.trafficassistant.repository.jpa.JpaUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final JpaUserRepository userRepository;

    public UserService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String fullName, String username, String email, String password) throws InvalidCharacterInUsernameException
    {
        for (char c : username.toCharArray())
        {
            if (!Character.isLetterOrDigit(c) && c != '_')
                throw new InvalidCharacterInUsernameException();
        }
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

    public User logIn(String usernameOrEmail, String password) throws InvalidCharacterInUsernameException
    {
        for (char c : usernameOrEmail.toCharArray())
        {
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '.' && c != '@')
                throw new InvalidCharacterInUsernameException();
        }
        if (usernameOrEmail.contains("@"))
            return userRepository.getByEmailAndPassword(usernameOrEmail, password);
        else
            return userRepository.getByUsernameAndPassword(usernameOrEmail, password);
    }

    @Transactional
    public User register(String fullName, String username, String email, String password) throws InvalidCharacterInUsernameException
    {
        addUser(fullName, username, email, password);
        return logIn(username, password);
    }
}
