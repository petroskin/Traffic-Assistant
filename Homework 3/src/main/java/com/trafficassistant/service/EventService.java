package com.trafficassistant.service;

import com.trafficassistant.model.Event;
import com.trafficassistant.model.User;
import com.trafficassistant.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void addEvent(User user, Double latitude, Double longitude, Integer type, LocalDateTime time, Boolean valid, String comment){
        eventRepository.addEvent(user, latitude, longitude, type, time, valid, comment);
    }

    public List<Event> getEvents()
    {
        return eventRepository.getEvents();
    }
}
