package com.trafficassistant.userservice.service.impl;

import com.trafficassistant.userservice.model.Report;
import com.trafficassistant.userservice.model.User;
import com.trafficassistant.userservice.repository.ReportRepository;
import com.trafficassistant.userservice.repository.UserRepository;
import com.trafficassistant.userservice.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService
{
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository)
    {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Report> getReports()
    {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> getByReportedUser(String username)
    {
        User user = userRepository.getByUsername(username);
        return reportRepository.getByReportedUser(user);
    }

    @Override
    public List<Report> getByDate(LocalDateTime date)
    {
        return reportRepository.getByDate(date);
    }

    @Override
    public Report addReport(String reportedUserUN, String userReportingUN, Long eventId, LocalDateTime date, String comment)
    {
        User reportedUser = userRepository.getByUsername(reportedUserUN);
        User userReporting = userRepository.getByUsername(userReportingUN);
        return reportRepository.save(new Report(reportedUser, userReporting, eventId, comment, date));
    }

    @Override
    @Transactional
    public Report removeReport(Long id)
    {
        List<Report> reports = reportRepository.removeById(id);
        if (reports == null || reports.isEmpty())
            return null;
        return reports.get(0);
    }
}
