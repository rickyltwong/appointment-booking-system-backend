package project.group14.authenticationservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.group14.authenticationservice.model.User;
import project.group14.authenticationservice.service.ApplicationService;
import project.group14.authenticationservice.dto.AppointmentDTO;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
public class UserController {


    private final ApplicationService userService;

    @Autowired
    public UserController(ApplicationService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    @ResponseBody
    public String testUserAccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            System.out.println("User: " + user.getUsername() + ", Roles: " + user.getAuthorities());
            return "User ID: " + user.getId() + ", Roles: " + user.getAuthorities();
        }
        return "User not authenticated";
    }
}
