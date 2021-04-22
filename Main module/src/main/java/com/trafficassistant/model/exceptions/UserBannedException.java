package com.trafficassistant.model.exceptions;

import java.time.LocalDateTime;

public class UserBannedException extends Exception
{
    public UserBannedException(String user, LocalDateTime dateTime)
    {
        super(user + " is banned until " + dateTime.toString() + "!");
    }
}
