package com.trafficassistant.userservice.service;

import com.trafficassistant.userservice.model.Report;
import com.trafficassistant.userservice.model.User;
import com.trafficassistant.userservice.repository.ReportRepository;
import com.trafficassistant.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class ReportService
{
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository)
    {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public List<Report> getReports()
    {
        return reportRepository.findAll();
    }

    public List<Report> getByReportedUser(String username)
    {
        User user = userRepository.getByUsername(username);
        return reportRepository.getByReportedUser(user);
    }

    public List<Report> getByDate(Date date)
    {
        return reportRepository.getByDate(date);
    }

    public Report addReport(String reportedUserUN, String userReportingUN, Long eventId, Date date, String comment)
    {
        User reportedUser = userRepository.getByUsername(reportedUserUN);
        User userReporting = userRepository.getByUsername(userReportingUN);
        return reportRepository.save(new Report(reportedUser, userReporting, eventId, comment, date));
    }
}
