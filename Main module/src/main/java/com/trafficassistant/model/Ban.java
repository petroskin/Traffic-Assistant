package com.trafficassistant.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Ban
{
    Long Id;
    User bannedUser;
    User adminBanning;
    Date date;

    public Ban(User bannedUser, User adminBanning, Date date)
    {
        this.bannedUser = bannedUser;
        this.adminBanning = adminBanning;
        this.date = date;
    }
}
