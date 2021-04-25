package com.trafficassistant.web.controllers;

import com.trafficassistant.model.Event;
import com.trafficassistant.model.exceptions.EventDoesNotExistException;
import com.trafficassistant.service.EventService;
import com.trafficassistant.service.ReportService;
import com.trafficassistant.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/report")
public class ReportController {
    private final EventService eventService;
    private final ReportService reportService;
    private final UserService userService;

    public ReportController(EventService eventService, ReportService reportService, UserService userService) {
        this.eventService = eventService;
        this.reportService = reportService;
        this.userService = userService;
    }

    @GetMapping(path="/{eventId}")
    public String reportUser(@PathVariable Long eventId, Model model, HttpServletRequest req) throws EventDoesNotExistException {
        Event e = eventService.findById(eventId);
        if(req.getRemoteUser().equals(e.getUserName()))
            return "redirect:/"; //user cannot report itself
        if(userService.getByUsername(e.getUserName()).getAdmin())
            return "reportAdmin"; //admin event cannot be reported
        model.addAttribute("eventId", e.getId());
        model.addAttribute("username", e.getUserName());
        model.addAttribute("eventType", e.getTypeEnum().toString() + " event on ");
        model.addAttribute("eventTime", e.getTime().toString());
        model.addAttribute("eventComment", e.getComment());
        return "report";
    }

    @PostMapping
    public String reportUserPost(@RequestParam Long eventId, @RequestParam String comment, HttpServletRequest req) throws EventDoesNotExistException {
        Event e = eventService.findById(eventId);
        reportService.addReport(e.getUserName(), req.getRemoteUser(), e.getId(), LocalDateTime.now(), comment);
        return "redirect:/";
    }
}
