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

    public void addEvent(User user, String name, Double latitude, Double longitude, int type, LocalDateTime time, String comment, Integer likes, Integer dislikes, Integer ttl){
        EventTypeEnum typeEnum = EventTypeEnum.values()[type]; //type is 0 to 7 for now
        eventRepository.save(new Event(user, name, latitude, longitude, typeEnum, time, comment, likes, dislikes, ttl));
    }

    public List<Event> getEvents()
    {
        return eventRepository.findAll();
    }
}
