package com.trafficassistant.userservice.service.impl;

import com.trafficassistant.userservice.model.User;
import com.trafficassistant.userservice.repository.UserRepository;
import com.trafficassistant.userservice.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public String addUser(User user)
    {
        StringBuilder returnStatus = new StringBuilder("ok");
        if (!taken(user, returnStatus))
            this.userRepository.save(user);
        return returnStatus.toString();
    }

//    Not used

//    public List<User> getUsers()
//    {
//        return this.userRepository.findAll();
//    }

    @Override
    public User getByUsername(String username)
    {
        User get = userRepository.findById(username).orElse(null);
        return get == null ? null : userToReturn(get);
    }

    @Override
    public User logIn(String info)
    {
        String[] credentials = info.split("\t");
        User ret = loginCheck(credentials);
        if (ret == null)
            return null;
        return userToReturn(ret);
    }

    @Override
    @Transactional
    public User register(User user)
    {
        String ret = addUser(user);
        if (ret.equals("ok"))
            return logIn(user.getUsername() + "\t" + user.getPassword());
        return new User(ret, null, null, null);
    }

    private User loginCheck(String [] credentials)
    {
        if (credentials[0].contains("@"))
            return loginWithEmailCheck(credentials);
        else
            return loginWithUsernameCheck(credentials);
    }

    private boolean taken(User user, StringBuilder status)
    {
        if (emailTaken(user.getEmail()))
        {
            status.setLength(0);
            status.append("EmailTakenException");
        }
        if (usernameTaken(user.getUsername()))
        {
            status.setLength(0);
            status.append("UsernameTakenException");
        }
        return !status.toString().equals("ok");
    }

    private User loginWithEmailCheck(String [] credentials)
    {
        return userRepository.getByEmailAndPassword(credentials[0], credentials[1]);
    }

    private User loginWithUsernameCheck(String [] credentials)
    {
        return userRepository.getByUsernameAndPassword(credentials[0], credentials[1]);
    }

    private boolean emailTaken(String email)
    {
        return userRepository.getByEmail(email) != null;
    }

    private boolean usernameTaken(String username)
    {
        return userRepository.getByUsername(username) != null;
    }

    private User userToReturn(User user)
    {
        User ret = new User(user.getFullName(), user.getUsername(), user.getEmail(), user.getPassword());
        ret.setAdmin(user.getAdmin());
        return ret;
    }
}
