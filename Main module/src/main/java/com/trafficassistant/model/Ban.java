package com.trafficassistant.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class Ban
{
    Long Id;
    User bannedUser;
    User adminBanning;
    LocalDateTime date;

    public Ban(User bannedUser, User adminBanning, LocalDateTime date)
    {
        this.bannedUser = bannedUser;
        this.adminBanning = adminBanning;
        this.date = date;
    }
}
