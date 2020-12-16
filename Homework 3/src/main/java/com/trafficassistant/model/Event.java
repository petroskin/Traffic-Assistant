package com.trafficassistant.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {
    Long id;
    User user;
    Double latitude;
    Double longitude;
    Integer type;
    LocalDateTime time;
    Boolean valid;
    String comment;
    Integer likes;
    Integer dislikes;

    public Event(User user, Double latitude, Double longitude, Integer type, LocalDateTime time, Boolean valid, String comment) {
        this.id = Math.round(Math.random()*100);
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.time = time;
        this.valid = valid;
        this.comment = comment;
    }
}
