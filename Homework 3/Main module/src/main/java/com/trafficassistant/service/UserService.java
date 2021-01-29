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
        check(usernameOrEmail, true);
        return restTemplate.postForObject("http://USER-SERVICE/users-rest/login", usernameOrEmail + "\t" + password, User.class);
    }

    public User register(String fullName, String username, String email, String password) throws InvalidCharacterInUsernameException, UsernameTakenException, EmailTakenException {
        check(username, false);
        User user = restTemplate.postForObject("http://USER-SERVICE/users-rest/register", new User(fullName, username, email, password), User.class);
        if (user == null) return null;
        if (user.getUsername() == null)
        {
            if (user.getFullName().equals("UsernameTakenException")) throw new UsernameTakenException(username);
            if (user.getFullName().equals("EmailTakenException")) throw new EmailTakenException(email);
        }
        return user;
    }

    public User getByUsername(String username)
    {
        return restTemplate.postForObject("http://USER-SERVICE/users-rest/get-by-username", username, User.class);
    }

    private void check(String credentials, boolean email) throws InvalidCharacterInUsernameException
    {
        for (char c : credentials.toCharArray())
        {
            if (email)
                checkWithEmail(c);
            else
                checkNoEmail(c);
        }
    }

    private void checkWithEmail(char c) throws InvalidCharacterInUsernameException
    {
        if (!Character.isLetterOrDigit(c) && c != '_' && c != '.' && c != '@')
            throw new InvalidCharacterInUsernameException();
    }

    private void checkNoEmail(char c) throws InvalidCharacterInUsernameException
    {
        if (!Character.isLetterOrDigit(c) && c != '_')
            throw new InvalidCharacterInUsernameException();
    }
}
