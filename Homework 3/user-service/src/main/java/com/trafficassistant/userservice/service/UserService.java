package com.trafficassistant.userservice.service;

import com.trafficassistant.userservice.model.User;
import com.trafficassistant.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public String addUser(User user) {
        if (userRepository.getByUsername(user.getUsername()) != null)
            return "UsernameTakenException";
        if (userRepository.getByEmail(user.getEmail()) != null)
            return "EmailTakenException";
        this.userRepository.save(user);
        return "ok";
    }

    public List<User> getUsers()
    {
        return this.userRepository.findAll();
    }

    public User getByUsername(String username)
    {
        return userRepository.findById(username).orElse(null);
    }

    public User logIn(String info)
    {
        String [] infos = info.split("\t");
        User ret = null;
        if (infos[0].contains("@"))
            ret = userRepository.getByEmailAndPassword(infos[0], infos[1]);
        else
            ret = userRepository.getByUsernameAndPassword(infos[0], infos[1]);
        if (ret == null) return null;
        return new User(ret.getFullName(), ret.getUsername(), ret.getEmail(), "");
    }

    @Transactional
    public User register(User user) {
        String ret = addUser(user);
        if (ret.equals("ok"))
            return logIn(user.getUsername() + "\t" + user.getPassword());
        User retUser = new User();
        retUser.setUsername(null);
        retUser.setFullName(ret);
        return retUser;
    }
}
