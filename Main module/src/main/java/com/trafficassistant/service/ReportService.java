package com.trafficassistant.service;

import com.trafficassistant.model.Report;
import com.trafficassistant.model.User;
import com.trafficassistant.model.wrapper.ReportList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ReportService
{
    @Autowired
    private RestTemplate restTemplate;

    public List<Report> getReports()
    {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(
                "http://USER-SERVICE/reports-rest/get-reports",
                Report[].class)));
    }

    public List<Report> getByReportedUser(String username)
    {
        return Arrays.asList(Objects.requireNonNull(restTemplate.postForObject(
                "http://USER-SERVICE/reports-rest/get-by-reported-user",
                username,
                Report[].class)));
    }

    public List<Report> getByReportedUser(User user)
    {
        return getByReportedUser(user.getUsername());
    }

    public List<Report> getByDate(Date date)
    {
        return Objects.requireNonNull(restTemplate.postForObject(
                "http://USER-SERVICE/reports-rest/get-by-date",
                date,
                ReportList.class)).getReports();
    }

    public Report addReport(Report report)
    {
        return restTemplate.postForObject(
                "http://USER-SERVICE/reports-rest/add-report",
                report,
                Report.class);
    }

    public Report addReport(String reportedUserUN, String userReportingUN, Long eventId, LocalDateTime date, String comment)
    {
        User reportedUser = new User("", reportedUserUN, "", "");
        User userReporting = new User("", userReportingUN, "", "");
        return addReport(new Report(reportedUser, userReporting, eventId, comment, date));
    }

    public Report removeReport(Long id)
    {
        return restTemplate.postForObject(
                "http://USER-SERVICE/reports-rest/remove-report",
                id,
                Report.class);
    }

    public Report removeReport(Report report)
    {
        return removeReport(report.getId());
    }
}
