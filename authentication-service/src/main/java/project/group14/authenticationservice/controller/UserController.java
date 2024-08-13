package project.group14.authenticationservice.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/test")
    public String getUserHome() {
        return "Welcome to the user home page!";
    }

    @GetMapping("/test-id")

}
