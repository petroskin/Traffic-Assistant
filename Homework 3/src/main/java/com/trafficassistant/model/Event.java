package com.trafficassistant.model;

import com.trafficassistant.model.enums.EventTypeEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Entity
public class Event {
    @Id
    Long id;
    @ManyToOne
    User user;
    Double latitude;
    Double longitude;
    EventTypeEnum typeEnum;
    LocalDateTime time;
    Boolean valid;
    String comment;
    Integer likes;
    Integer dislikes;

    public Event(User user, Double latitude, Double longitude, EventTypeEnum type, LocalDateTime time, Boolean valid, String comment) {
        this.id = Math.round(Math.random()*100);
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.typeEnum = type;
        this.time = time;
        this.valid = valid;
        this.comment = comment;
    }

    // Constructor needed for JPA
    public Event()
    {
    }
}
