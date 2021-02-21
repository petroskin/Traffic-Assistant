package com.trafficassistant.web.controllers;

import com.trafficassistant.model.Event;
import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.EventDoesNotExistException;
import com.trafficassistant.model.exceptions.EventNotOnRoadException;
import com.trafficassistant.service.EventService;
import com.trafficassistant.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public String mainPage(HttpServletRequest req)
    {
        if (req.getRemoteUser() == null || "".equals(req.getRemoteUser()))
            return "redirect:/welcome";
        //
        // RESOLVE DEPENDENCIES FOR USER IN SESSION IN /index - REMOVE FOLLOWING LINES AFTER
        //
        User current = userService.getByUsername(req.getRemoteUser());
        User forSession =  new User(current.getFullName(), current.getUsername(), current.getEmail(), "");
        forSession.setAdmin(current.getAdmin());
        req.getSession().setAttribute("currentUser", forSession);
        //
        // STOP REMOVING
        //
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
    public String createEvent(HttpServletRequest req,
                              @RequestParam String name,
                              @RequestParam String latitude,
                              @RequestParam String longitude,
                              @RequestParam String type,
                              @RequestParam String comment)
    {
        try {
            eventService.addEvent(req.getRemoteUser(),
                    name,
                    Double.parseDouble(latitude),
                    Double.parseDouble(longitude),
                    Integer.parseInt(type)-1,
                    LocalDateTime.now(),
                    comment,
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
    public String deleteEvent(HttpServletRequest req, @RequestParam Long id){
        User currentUser = userService.getByUsername(req.getRemoteUser());
        Event event;
        try {
            event = eventService.findById(id);
        }catch(EventDoesNotExistException ex){
            return ex.getMessage();
        }
        if(event.getUserName().equals(currentUser.getUsername()) || currentUser.getAdmin()) {
            eventService.deleteById(id);
            return "success";
        }
        else {
            return "You cannot do this action.";
        }
    }

    @GetMapping(path="/getLikesDislikes")
    @ResponseBody
    public List<String> getLikesDislikes(@RequestParam Long id){
        Event event;
        try {
            event = eventService.findById(id);
        }catch(EventDoesNotExistException ex){
            List<String> lol = new ArrayList<>();
            lol.add("no event with such id sorry mate");
            return lol;
        }
        return event.getUsersLikeDislike().entrySet().stream().map(entry -> entry.getKey() + " " + (entry.getValue()?"1":"0")).collect(Collectors.toList());
    }

    @GetMapping(path="/likeOrDislike")
    @ResponseBody
    public String likeOrDislike(HttpServletRequest req, @RequestParam Long id, @RequestParam String like)//like=1, dislike=0, removeVote=-1
    {
        try {
            eventService.likeOrDislikeEvent(id, req.getRemoteUser(), like);
            eventService.resetTTLById(id);
        }
        catch(EventDoesNotExistException ex){
            return "Event was not found.";
        }
        return "success";
    }
}
