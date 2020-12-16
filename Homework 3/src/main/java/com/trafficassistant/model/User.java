package com.trafficassistant.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
//TODO for some reason table name cannot be resolved
@Table(name = "TA_Users")
public class User {
    @Id
    String username;
    String fullName;
    String email;
    String password;

    public User(String fullName, String username, String email, String password) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Constructor needed for JPA

    public User()
    {
    }
}
