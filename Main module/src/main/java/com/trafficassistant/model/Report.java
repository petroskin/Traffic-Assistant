package com.trafficassistant.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Report
{
    Long Id;
    User reportedUser;
    User userReporting;
    String comment;
    Date date;
    Long eventId;

    public Report(User reportedUser, User userReporting, Long eventId, String comment, Date date)
    {
        this.reportedUser = reportedUser;
        this.userReporting = userReporting;
        this.comment = comment;
        this.date = date;
        this.eventId = eventId;
    }
}
