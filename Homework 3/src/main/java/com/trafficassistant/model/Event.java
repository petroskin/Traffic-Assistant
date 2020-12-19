package com.trafficassistant.model;

import com.trafficassistant.model.enums.EventTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    User user;
    String name;
    Double latitude;
    Double longitude;
    EventTypeEnum typeEnum;
    LocalDateTime time;
    Boolean valid;
    String comment;
    Integer likes;
    Integer dislikes;
    Integer ttl; //in minutes time left until event is valid and exists (default 30, reset to 30 on each interaction with user)

    public Event(User user, String name, Double latitude, Double longitude, EventTypeEnum typeEnum, LocalDateTime time, String comment, Integer likes, Integer dislikes, Integer ttl) {
        this.user = user;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.typeEnum = typeEnum;
        this.time = time;
        this.comment = comment;
        this.likes = likes;
        this.dislikes = dislikes;
        this.ttl = ttl;
    }

    // Constructor needed for JPA
    public Event()
    {
    }
}
