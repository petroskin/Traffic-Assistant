package com.trafficassistant.service;

import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.EmailTakenException;
import com.trafficassistant.model.exceptions.InvalidCharacterInUsernameException;
import com.trafficassistant.model.exceptions.UsernameTakenException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService
{
    public User logIn(String usernameOrEmail, String password) throws InvalidCharacterInUsernameException;

    public User register(String fullName, String username, String email, String password) throws InvalidCharacterInUsernameException, UsernameTakenException, EmailTakenException;

    public User getByUsername(String username);
}
