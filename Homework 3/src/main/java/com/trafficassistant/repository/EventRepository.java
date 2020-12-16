package com.trafficassistant.repository;

import com.trafficassistant.model.Event;
import com.trafficassistant.model.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepository {

    private static List<Event> events = new ArrayList<>();

    public List<Event> getEvents()
    {
        return events;
    }

    public void addEvent(User user, Double latitude, Double longitude, Integer type, LocalDateTime time, Boolean valid, String comment){
        events.add(new Event(user, latitude, longitude, type, time, valid, comment));
    }
}
