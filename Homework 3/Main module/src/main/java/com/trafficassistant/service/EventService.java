package com.trafficassistant.service;

import com.trafficassistant.model.Event;
import com.trafficassistant.model.RoadNode;
import com.trafficassistant.model.enums.EventTypeEnum;
import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.EventDoesNotExistException;
import com.trafficassistant.model.exceptions.EventNotOnRoadException;
import com.trafficassistant.repository.RoadRepository;
import com.trafficassistant.repository.jpa.JpaEventRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final JpaEventRepository eventRepository;
    private final Collection<RoadNode> inMemoryNodes;

    public EventService(JpaEventRepository eventRepository, RoadRepository roadRepository) throws FileNotFoundException
    {
        this.eventRepository = eventRepository;
        inMemoryNodes = roadRepository.getNodes();
    }

    public void addEvent(User user, String name, Double latitude, Double longitude, int type, LocalDateTime time, String comment, Integer ttl) throws EventNotOnRoadException
    {
        if (!isOnRoad(latitude, longitude, inMemoryNodes))
                throw new EventNotOnRoadException("Event " + name + " must be located on a roadway.");

        EventTypeEnum typeEnum = EventTypeEnum.values()[type]; //type is 0 to 7 for now
        eventRepository.save(new Event(user.getUsername(), name, latitude, longitude, typeEnum, time, comment, ttl));
    }

    public List<Event> getEvents()
    {
        return eventRepository.findAll();
    }

    // Checks whether a point on the map is on top of a road node, as taken by osmfilter.
    public static boolean isOnRoad(Double lat, Double lon, Collection<RoadNode> nodes)
    {
        // 0.007 correctness handled in RoadNode class
        return nodes.contains(new RoadNode(lon, lat));
    }

    public void deleteById(Long id){
        eventRepository.deleteById(id);
    }

    public Event findById(Long id) throws EventDoesNotExistException {
        return eventRepository.findById(id).orElseThrow(() -> new EventDoesNotExistException(id));
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 10000)
    public void decreaseTTLOnEventsByOneAndRemoveExpired(){
        List<Event> allEvents = eventRepository.findAll();
        allEvents.forEach(event -> {
            event.setTtl(event.getTtl()-1);
            if(event.getTtl()==0){
                eventRepository.deleteById(event.getId());
            }
        });
        eventRepository.saveAll(allEvents.stream().filter(event -> event.getTtl()!=0).collect(Collectors.toList()));
    }

    public void resetTTLById(Long id) throws EventDoesNotExistException {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventDoesNotExistException(id));
        event.setTtl(30);
        eventRepository.save(event);
    }

    public void likeOrDislikeEvent(Long id, String username, String like) throws EventDoesNotExistException {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventDoesNotExistException(id));
        if(like.equals("1"))
            event.getUsersLikeDislike().put(username, true);
        else if(like.equals("0"))
            event.getUsersLikeDislike().put(username, false);
        else
            event.getUsersLikeDislike().remove(username);
        eventRepository.save(event);
    }
}
