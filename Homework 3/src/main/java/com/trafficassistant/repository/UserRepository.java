package com.trafficassistant.repository;

import com.trafficassistant.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private static List<User> users = new ArrayList<>();

    public List<User> getUsers()
    {
        return users;
    }

    public void addUser(String fullName, String username, String email, String password)
    {
        users.add(new User(fullName, username, email, password));
    }
}
