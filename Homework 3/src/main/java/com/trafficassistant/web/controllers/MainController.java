package com.trafficassistant.web.controllers;

import com.trafficassistant.model.Event;
import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.EventNotOnRoadException;
import com.trafficassistant.service.EventService;
import com.trafficassistant.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

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
    }

    @GetMapping
    public String mainPage(Model model, HttpServletRequest req)
    {
        //if (req.getSession().getAttribute("currentUser") == null)
            //return "redirect:/welcome";
        return "index";
    }

    @GetMapping(path = "/welcome")
    public String welcomePage()
    {
        return "welcome";
    }

    @GetMapping(path = "/contact")
    public String contactPage()
    {
        return "contact";
    }

    @GetMapping(path = "/about")
    public String aboutPage()
    {
        return "about";
    }

    @GetMapping(path="/createEvent")
    @ResponseBody
    public String createEvent(Model model,
                              @RequestParam String name,
                              @RequestParam String latitude,
                              @RequestParam String longitude,
                              @RequestParam String type,
                              @RequestParam String comment)
    {
        try {
            eventService.addEvent((User) model.getAttribute("currentUser"),
                    name,
                    Double.parseDouble(latitude),
                    Double.parseDouble(longitude),
                    Integer.parseInt(type)-1,
                    LocalDateTime.now(),
                    comment,
                    0,
                    0,
                    30);
        }
        catch(EventNotOnRoadException ex){
            return ex.getMessage();
        }
        return "success";
    }

    @GetMapping(path="/getAllEvents")
    @ResponseBody
    public List<Event> getAllEvents(){
        return eventService.getEvents();
    }


    @GetMapping(path="/deleteEvent")
    @ResponseBody
    public String deleteEvent(@RequestParam Long id){
        eventService.deleteById(id);
        return "success"; //not fully implemented
    }
}
