package com.trafficassistant.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Report
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @ManyToOne
    User reportedUser;
    @ManyToOne
    User userReporting;
    String comment;
    Date date;

    public Report(User reportedUser, User userReporting, String comment, Date date)
    {
        this.reportedUser = reportedUser;
        this.userReporting = userReporting;
        this.comment = comment;
        this.date = date;
    }
}
