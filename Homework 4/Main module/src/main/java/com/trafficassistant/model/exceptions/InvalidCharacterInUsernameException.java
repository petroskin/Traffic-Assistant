package com.trafficassistant.model.exceptions;

public class InvalidCharacterInUsernameException extends Exception
{
    public InvalidCharacterInUsernameException()
    {
        super("Username must contain only letters, numbers and underscores.");
    }
}
