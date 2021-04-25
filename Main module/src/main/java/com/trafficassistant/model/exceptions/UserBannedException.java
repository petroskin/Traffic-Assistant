package com.trafficassistant.model.exceptions;

import java.time.Duration;
import java.time.LocalDateTime;

public class UserBannedException extends Exception
{
    public UserBannedException(String message)
    {
        super(message);
    }
}
