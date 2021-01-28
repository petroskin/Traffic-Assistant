package com.trafficassistant.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// This is value object class
@Data
public class User {
    String username;
    String fullName;
    String email;
    String password;
    Boolean admin;

    public User(String fullName, String username, String email, String password) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.admin = false;
    }

    // Constructor needed for JPA

    public User()
    {
    }
}