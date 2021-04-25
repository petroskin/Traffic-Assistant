package com.trafficassistant.web.controllers;

import com.trafficassistant.model.Event;
import com.trafficassistant.model.Report;
import com.trafficassistant.model.ReportEventDto;
import com.trafficassistant.model.exceptions.NoBanPrivilegeException;
import com.trafficassistant.service.BanService;
import com.trafficassistant.service.EventService;
import com.trafficassistant.service.ReportService;
import com.trafficassistant.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ReportService reportService;
    private final BanService banService;
    private final UserService userService;
    private final EventService eventService;

    public AdminController(ReportService reportService, BanService banService, UserService userService, EventService eventService) {
        this.reportService = reportService;
        this.banService = banService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping
    public String adminPage(Model model, HttpServletRequest req){
        if(!userService.getByUsername(req.getRemoteUser()).getAdmin())
            return "redirect:/";
        List<Report> allReports = reportService.getReports();
        List<Long> reportedEventsIds = allReports.stream().map(Report::getEventId).collect(Collectors.toList());
        List<Event> reportedEvents = eventService.getEventsWithIds(reportedEventsIds);
        model.addAttribute("reports", allReports.stream()
        .map(report -> {
            Event temp = reportedEvents.stream().filter(event -> event.getId().equals(report.getEventId())).findFirst().get();
            return new ReportEventDto(report.getReportedUser().getUsername(),
                    report.getDate(),
                    report.getComment(),
                    temp.getTypeEnum().name(),
                    temp.getTime(),
                    temp.getComment(),
                    temp.getUsersLikeDislike());
        }).collect(Collectors.toList()));

        return "admin";
    }

    private void banUser(String username, HttpServletRequest req) throws NoBanPrivilegeException {
        banService.addBan(username, req.getRemoteUser(), LocalDateTime.now().plusDays(7));
        List<Event> eventsToRemove = eventService.getEvents().stream()
                .filter(event -> event.getUserName().equals(username)).collect(Collectors.toList());
        List<Report> reportsToRemove = reportService.getReports().stream()
                .filter(report -> report.getReportedUser().getUsername().equals(username)).collect(Collectors.toList());
        eventsToRemove.forEach(event -> eventService.deleteById(event.getId()));
        reportsToRemove.forEach(report -> reportService.removeReport(report.getId()));
    }

    @PostMapping(path="/ban")
    public String banFromAdmin(@RequestParam String username, HttpServletRequest req) throws NoBanPrivilegeException {
        banUser(username, req);
        return "redirect:/admin";
    }

    @PostMapping(path="/banfromindex")
    public String banFromIndex(@RequestParam String username, HttpServletRequest req) throws NoBanPrivilegeException {
        banUser(username, req);
        return "redirect:/";
    }
}
