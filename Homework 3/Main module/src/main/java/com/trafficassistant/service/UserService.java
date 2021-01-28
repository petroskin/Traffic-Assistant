package com.trafficassistant.service;

import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.EmailTakenException;
import com.trafficassistant.model.exceptions.InvalidCharacterInUsernameException;
import com.trafficassistant.model.exceptions.UsernameTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    public User logIn(String usernameOrEmail, String password) throws InvalidCharacterInUsernameException
    {
        for (char c : usernameOrEmail.toCharArray())
        {
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '.' && c != '@')
                throw new InvalidCharacterInUsernameException();
        }
        return restTemplate.postForObject("http://USER-SERVICE/users-rest/login", usernameOrEmail + "\t" + password, User.class);
    }

    public User register(String fullName, String username, String email, String password) throws InvalidCharacterInUsernameException, UsernameTakenException, EmailTakenException {
        for (char c : username.toCharArray())
        {
            if (!Character.isLetterOrDigit(c) && c != '_')
                throw new InvalidCharacterInUsernameException();
        }
        User user = restTemplate.postForObject("http://USER-SERVICE/users-rest/register", new User(fullName, username, email, password), User.class);
        if (user == null) return null;
        if (user.getUsername() == null)
        {
            if (user.getFullName().equals("UsernameTakenException")) throw new UsernameTakenException(username);
            if (user.getFullName().equals("EmailTakenException")) throw new EmailTakenException(email);
        }
        return user;
    }
}
