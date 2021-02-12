package com.trafficassistant.model.exceptions;

public class EmailTakenException extends Exception {
    public EmailTakenException(String email){
        super("The email " + email + " is already taken!");
    }
}
