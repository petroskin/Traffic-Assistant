package com.trafficassistant.model.exceptions;

public class UsernameTakenException extends Exception
{
    public UsernameTakenException(String message)
    {
        super("The username " + message + " is already taken!");
    }
}
