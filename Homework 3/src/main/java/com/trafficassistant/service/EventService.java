package com.trafficassistant.service;

import com.trafficassistant.model.Event;
import com.trafficassistant.model.enums.EventTypeEnum;
import com.trafficassistant.model.User;
import com.trafficassistant.repository.EventRepository;
import com.trafficassistant.repository.jpa.JpaEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final JpaEventRepository eventRepository;

    public EventService(JpaEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void addEvent(User user, Double latitude, Double longitude, Integer type, LocalDateTime time, Boolean valid, String comment){
        EventTypeEnum typeEnum = EventTypeEnum.values()[type];
        eventRepository.save(new Event(user, latitude, longitude, typeEnum, time, valid, comment));
    }

    public List<Event> getEvents()
    {
        return eventRepository.findAll();
    }
}
