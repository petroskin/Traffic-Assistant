package com.trafficassistant.web.controllers;

import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.EmailTakenException;
import com.trafficassistant.model.exceptions.InvalidCharacterInUsernameException;
import com.trafficassistant.model.exceptions.UsernameTakenException;
import com.trafficassistant.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String loginPage(HttpServletRequest req, @RequestParam(required = false) String error, Model model)
    {
        if (req.getRemoteUser() != null && !"".equals(req.getRemoteUser()))
            return "redirect:/";
        if (error != null)
            model.addAttribute("errorMessage", "Unrecognised username and/or password");
        return "log_in";
    }

    @GetMapping(path = "/register")
    public String registerPage(HttpServletRequest req)
    {
        if (req.getRemoteUser() != null && !"".equals(req.getRemoteUser()))
            return "redirect:/";
        return "sign_up";
    }

    @PostMapping(path = "/register")
    public String registerUser(HttpServletRequest req,
                               Model model,
                               @RequestParam String fullName,
                               @RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password) throws InvalidCharacterInUsernameException
    {
        StringBuilder attributeCheckMessage = new StringBuilder("");
        if (!checkValidity(new User(fullName, username, email, password), attributeCheckMessage))
        {
            model.addAttribute("errorMessage", attributeCheckMessage.toString());
            return "sign_up";
        }
        try
        {
            userService.register(fullName, username, email, password);
        }
        catch (UsernameTakenException | EmailTakenException e)
        {
            model.addAttribute("errorMessage", e.getMessage());
            return "sign_up";
        }
        return "redirect:/user/login";
    }

    private boolean checkValidity(User user, StringBuilder sb)
    {
        if (isValid(user.getFullName(), false))
            sb.append("Name must only contain letters and space\n");
        if (isValid(user.getUsername(), true))
            sb.append("Username must contain only letters, numbers and underscores.\n");
        return sb.toString().equals("");
    }

    private boolean isValid(String credential, boolean username)
    {
        for (char c : credential.toCharArray())
        {
            if (username ? invalidUsername(c) : invalidName(c))
                return false;
        }
        return false;
    }

    private boolean invalidName(char c)
    {
        return !Character.isLetter(c) && c != ' ';
    }

    private boolean invalidUsername(char c)
    {
        return !Character.isLetterOrDigit(c) && c != '_';
    }
}
