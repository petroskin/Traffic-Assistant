package com.trafficassistant.service.impl;

import com.trafficassistant.model.Report;
import com.trafficassistant.model.User;
import com.trafficassistant.model.wrapper.ReportList;
import com.trafficassistant.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ReportServiceImpl implements ReportService
{
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Report> getReports()
    {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(
                "http://USER-SERVICE/reports-rest/get-reports",
                Report[].class)));
    }

    @Override
    public List<Report> getByReportedUser(String username)
    {
        return Arrays.asList(Objects.requireNonNull(restTemplate.postForObject(
                "http://USER-SERVICE/reports-rest/get-by-reported-user",
                username,
                Report[].class)));
    }

    @Override
    public List<Report> getByReportedUser(User user)
    {
        return getByReportedUser(user.getUsername());
    }

    @Override
    public List<Report> getByDate(Date date)
    {
        return Objects.requireNonNull(restTemplate.postForObject(
                "http://USER-SERVICE/reports-rest/get-by-date",
                date,
                ReportList.class)).getReports();
    }

    @Override
    public Report addReport(Report report)
    {
        return restTemplate.postForObject(
                "http://USER-SERVICE/reports-rest/add-report",
                report,
                Report.class);
    }

    @Override
    public Report addReport(String reportedUserUN, String userReportingUN, Long eventId, LocalDateTime date, String comment)
    {
        User reportedUser = new User("", reportedUserUN, "", "");
        User userReporting = new User("", userReportingUN, "", "");
        return addReport(new Report(reportedUser, userReporting, eventId, comment, date));
    }

    @Override
    public Report removeReport(Long id)
    {
        return restTemplate.postForObject(
                "http://USER-SERVICE/reports-rest/remove-report",
                id,
                Report.class);
    }

    @Override
    public Report removeReport(Report report)
    {
        return removeReport(report.getId());
    }
}
