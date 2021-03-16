package com.trafficassistant.userservice.controller;

import com.trafficassistant.userservice.model.Report;
import com.trafficassistant.userservice.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reports-rest")
public class ReportController
{
    private final ReportService reportService;

    public ReportController(ReportService reportService)
    {
        this.reportService = reportService;
    }

    @GetMapping("/get-reports")
    public List<Report> getReports()
    {
        return reportService.getReports();
    }

    @PostMapping("get-by-reported-user")
    public List<Report> getByReportedUser(@RequestBody String username)
    {
        return reportService.getByReportedUser(username);
    }

    @PostMapping("get-by-date")
    public List<Report> getByDate(@RequestBody LocalDateTime date)
    {
        return reportService.getByDate(date);
    }

    @PostMapping("add-report")
    public Report addReport(@RequestBody Report report)
    {
        return reportService.addReport(
                report.getReportedUser().getUsername(),
                report.getUserReporting().getUsername(),
                report.getEventId(),
                report.getDate(),
                report.getComment());
    }
}
