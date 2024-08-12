package project.group14.authenticationservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.group14.authenticationservice.model.User;
import project.group14.authenticationservice.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") User user) {
        userService.registerNewUser(user);
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String getUserHome() {
        return "Welcome to the user home page!";
    }

    @GetMapping("/admin")
    public String getAdminHome() {
        return "Welcome to the admin home page!";
    }
}
