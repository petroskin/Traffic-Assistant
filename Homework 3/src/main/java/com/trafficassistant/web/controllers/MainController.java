package com.trafficassistant.web.controllers;

import com.trafficassistant.service.EventService;
import com.trafficassistant.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    private final EventService eventService;
    private final UserService userService;

    public MainController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
        userService.addUser("Stojko", "stojko123", "stojko@stojko", "stojkoekral");
    }

    @GetMapping
    public String mainPage(Model model)
    {
        model.addAttribute("users", userService.getUsers());
        return "index";
    }
}
