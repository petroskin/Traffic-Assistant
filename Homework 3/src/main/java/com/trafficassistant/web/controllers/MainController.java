package com.trafficassistant.web.controllers;

import com.trafficassistant.model.User;
import com.trafficassistant.service.EventService;
import com.trafficassistant.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
public class MainController
{

    private final EventService eventService;
    private final UserService userService;

    public MainController(EventService eventService, UserService userService)
    {
        this.eventService = eventService;
        this.userService = userService;
        userService.addUser("Stojko", "stojko123", "stojko@stojko", "stojkoekral");
    }

    @GetMapping
    public String mainPage(Model model)
    {
        if (!model.containsAttribute("currentUser"))
            model.addAttribute("currentUser", userService.getByUsername("stojko123"));
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("events", eventService.getEvents());
        return "index";
    }

    @PostMapping
    public String createEvent(Model model,
                              @RequestParam String latitude,
                              @RequestParam String longitude,
                              @RequestParam String type,
                              @RequestParam String comment)
    {
        eventService.addEvent((User) model.getAttribute("currentUser"),
                Double.parseDouble(latitude),
                Double.parseDouble(longitude),
                Integer.parseInt(type),
                LocalDateTime.now(),
                true,
                comment);
        return "redirect:/";
    }

    @GetMapping(path = "/map")
    public String map()
    {
        return "prototype1";
    }
}
