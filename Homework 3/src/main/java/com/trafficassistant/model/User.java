package com.trafficassistant.model;

import lombok.Data;

@Data
public class User {
    String fullName;
    String username;
    String email;
    String password;

    public User(String fullName, String username, String email, String password) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
