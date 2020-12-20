package com.trafficassistant.service;

import com.trafficassistant.model.Event;
import com.trafficassistant.model.RoadNode;
import com.trafficassistant.model.enums.EventTypeEnum;
import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.EventNotOnRoadException;
import com.trafficassistant.repository.RoadRepository;
import com.trafficassistant.repository.jpa.JpaEventRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class EventService {

    private final JpaEventRepository eventRepository;
    private final RoadRepository roadRepository;
    private final Collection<RoadNode> inMemoryNodes;

    public EventService(JpaEventRepository eventRepository, RoadRepository roadRepository) throws FileNotFoundException
    {
        this.eventRepository = eventRepository;
        this.roadRepository = roadRepository;
        inMemoryNodes = roadRepository.getNodes();
    }

    public void addEvent(User user, String name, Double latitude, Double longitude, int type, LocalDateTime time, String comment, Integer likes, Integer dislikes, Integer ttl) throws EventNotOnRoadException
    {
        if (!isOnRoad(latitude, longitude, inMemoryNodes))
                throw new EventNotOnRoadException("Event " + name + " must be located on a roadway.");

        EventTypeEnum typeEnum = EventTypeEnum.values()[type]; //type is 0 to 7 for now
        eventRepository.save(new Event(user, name, latitude, longitude, typeEnum, time, comment, likes, dislikes, ttl));
    }

    public List<Event> getEvents()
    {
        return eventRepository.findAll();
    }

    // Checks whether a point on the map is on top of a road node, as taken by osmfilter.
    public static boolean isOnRoad(Double lat, Double lon, Collection<RoadNode> nodes)
    {
        // 0.003 correctness handled in RoadNode class
        return nodes.contains(new RoadNode(lon, lat));
    }

    public void deleteById(Long id){
        eventRepository.deleteById(id);
    }
}
