package com.trafficassistant.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Ban
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @ManyToOne
    User bannedUser;
    @ManyToOne
    User adminBanning;
    LocalDateTime date;

    public Ban(User bannedUser, User adminBanning, LocalDateTime date)
    {
        this.bannedUser = bannedUser;
        this.adminBanning = adminBanning;
        this.date = date;
    }
}
