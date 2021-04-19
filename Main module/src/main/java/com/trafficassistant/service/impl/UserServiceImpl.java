package com.trafficassistant.service.impl;

import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.EmailTakenException;
import com.trafficassistant.model.exceptions.InvalidCharacterInUsernameException;
import com.trafficassistant.model.exceptions.UsernameTakenException;
import com.trafficassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    private RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User logIn(String usernameOrEmail, String password) throws InvalidCharacterInUsernameException
    {
        check(usernameOrEmail, true);
        return restTemplate.postForObject("http://USER-SERVICE/users-rest/login", usernameOrEmail + "\t" + password, User.class);
    }

    @Override
    public User register(String fullName, String username, String email, String password) throws InvalidCharacterInUsernameException, UsernameTakenException, EmailTakenException {
        check(username, false);
        User user = restTemplate.postForObject("http://USER-SERVICE/users-rest/register",
                new User(fullName, username, email, passwordEncoder.encode(password)),
                User.class);
        if (user == null) return null;
        if (user.getUsername() == null)
        {
            if (user.getFullName().equals("UsernameTakenException")) throw new UsernameTakenException(username);
            if (user.getFullName().equals("EmailTakenException")) throw new EmailTakenException(email);
        }
        return user;
    }

    @Override
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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
    {
        User user = this.getByUsername(s);
        if (user == null)
            throw new UsernameNotFoundException(s);
        List<SimpleGrantedAuthority> roles = new LinkedList<>();
        roles.add(user.getAdmin() ?
                new SimpleGrantedAuthority("ROLE_ADMIN") :
                new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                roles);
    }
}
