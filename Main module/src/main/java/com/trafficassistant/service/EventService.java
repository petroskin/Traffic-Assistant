package com.trafficassistant.service;

import com.trafficassistant.model.Event;
import com.trafficassistant.model.RoadNode;
import com.trafficassistant.model.exceptions.EventDoesNotExistException;
import com.trafficassistant.model.exceptions.EventNotOnRoadException;
import com.trafficassistant.model.exceptions.UserBannedException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventService
{
    public void addEvent(String username, String name, Double latitude, Double longitude, int type, LocalDateTime time, String comment, Integer ttl) throws EventNotOnRoadException, UserBannedException;

    public List<Event> getEvents();

    // Checks whether a point on the map is on top of a road node, as taken by osmfilter.
    public static boolean isOnRoad(Double lat, Double lon, Collection<RoadNode> nodes)
    {
        // 0.007 correctness handled in RoadNode class
        return nodes.contains(new RoadNode(lon, lat));
    }

    public void deleteById(Long id);

    public Event findById(Long id) throws EventDoesNotExistException;

    public void decreaseTTLOnEventsByOneAndRemoveExpired();

    public void resetTTLById(Long id) throws EventDoesNotExistException;

    public void likeOrDislikeEvent(Long id, String username, String like) throws EventDoesNotExistException;
}
