package com.trafficassistant.model.exceptions;

public class EventDoesNotExistException extends Exception {
    public EventDoesNotExistException(Long id){
        super("Event with id " + id + " does not exist.");
    }
}
