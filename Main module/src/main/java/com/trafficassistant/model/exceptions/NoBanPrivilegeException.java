package com.trafficassistant.model.exceptions;

public class NoBanPrivilegeException extends Exception
{
    public NoBanPrivilegeException()
    {
        super("You cannot do this - you are not admin!");
    }
}
