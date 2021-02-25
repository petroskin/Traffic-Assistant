package com.trafficassistant.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    Date date;

    public Ban(User bannedUser, User adminBanning, Date date)
    {
        this.bannedUser = bannedUser;
        this.adminBanning = adminBanning;
        this.date = date;
    }
}
