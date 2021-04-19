package com.trafficassistant.service;

import com.trafficassistant.model.Report;
import com.trafficassistant.model.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ReportService
{
    public List<Report> getReports();

    public List<Report> getByReportedUser(String username);

    public List<Report> getByReportedUser(User user);

    public List<Report> getByDate(Date date);

    public Report addReport(Report report);

    public Report addReport(String reportedUserUN, String userReportingUN, Long eventId, LocalDateTime date, String comment);

    public Report removeReport(Long id);

    public Report removeReport(Report report);
}
