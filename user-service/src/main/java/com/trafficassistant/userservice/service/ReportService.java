package com.trafficassistant.userservice.service;

import com.trafficassistant.userservice.model.Report;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService
{
    public List<Report> getReports();

    public List<Report> getByReportedUser(String username);

    public List<Report> getByDate(LocalDateTime date);

    public Report addReport(String reportedUserUN, String userReportingUN, Long eventId, LocalDateTime date, String comment);

    public Report removeReport(Long id);
}
