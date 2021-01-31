package com.trafficassistant.model;

import com.trafficassistant.model.enums.EventTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String userName;
    String name;
    Double latitude;
    Double longitude;
    EventTypeEnum typeEnum;
    LocalDateTime time;
    String comment;
    @ElementCollection
    Map<String, Boolean> usersLikeDislike;
    Integer ttl; //in minutes time left until event is valid and exists (default 30, reset to 30 on each interaction with user)

    public Event(String userName, String name, Double latitude, Double longitude, EventTypeEnum typeEnum, LocalDateTime time, String comment, Integer ttl) {
        this.userName = userName;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.typeEnum = typeEnum;
        this.time = time;
        this.comment = comment;
        this.usersLikeDislike = new HashMap<>();
        this.ttl = ttl;
    }

    // Constructor needed for JPA
    public Event()
    {
    }
}
