package com.trafficassistant.web.controllers;

import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.InvalidCharacterInUsernameException;
import com.trafficassistant.model.exceptions.UsernameTakenException;
import com.trafficassistant.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping(path = "/login")
    public String loginPage(HttpServletRequest req)
    {
        if (req.getSession().getAttribute("currentUser") != null)
            return "redirect:/";
        return "log_in";
    }

    @GetMapping(path = "/register")
    public String registerPage(HttpServletRequest req)
    {
        if (req.getSession().getAttribute("currentUser") != null)
            return "redirect:/";
        return "sign_up";
    }

    @PostMapping(path = "/login")
    public String loginUser(HttpServletRequest req,
                            HttpServletResponse resp,
                            Model model,
                            @RequestParam String username,
                            @RequestParam String password)
    {
        User logged;
        try
        {
            logged = userService.logIn(username, password);
        }
        catch (InvalidCharacterInUsernameException e)
        {
            model.addAttribute("errorMessage", e.getMessage());
            return "log_in";
        }
        if (logged == null)
        {
            model.addAttribute("errorMessage", "Unrecognised username and/or passowrd");
            return "log_in";
        }
        req.getSession().setAttribute("currentUser", logged);
        return "redirect:/";
    }

    @PostMapping(path = "/register")
    public String registerUser(HttpServletRequest req,
                               Model model,
                               @RequestParam String fullName,
                               @RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password) throws InvalidCharacterInUsernameException
    {
        boolean validArgs = true;
        String attributeCheckMessage = "";
        if (!isValidName(fullName))
        {
            validArgs = false;
            attributeCheckMessage += "Name must only contain letters and space\n";
        }
        if (!isValidUsername(username))
        {
            validArgs = false;
            attributeCheckMessage += "Username must contain only letters, numbers and underscores.\n";
        }
        if (!validArgs)
        {
            model.addAttribute("errorMessage", attributeCheckMessage);
            return "sign_up";
        }
        User logged;
        try
        {
            logged = userService.register(fullName, username, email, password);
        }
        catch (UsernameTakenException e)
        {
            model.addAttribute("errorMessage", e.getMessage());
            return "sign_up";
        }
        req.getSession().setAttribute("currentUser", logged);
        return "redirect:/";
    }

    private boolean isValidName(String name)
    {
        for (char c : name.toCharArray())
        {
            if (!Character.isLetter(c) && c != ' ')
                return false;
        }
        return true;
    }

    private boolean isValidUsername(String uname)
    {
        for (char c : uname.toCharArray())
        {
            if (!Character.isLetterOrDigit(c) && c != '_')
                return false;
        }
        return true;
    }
}
