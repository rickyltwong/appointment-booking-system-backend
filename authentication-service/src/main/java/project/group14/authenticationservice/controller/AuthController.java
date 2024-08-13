package project.group14.authenticationservice.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.group14.authenticationservice.model.User;
import project.group14.authenticationservice.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") User user) {
        userService.registerNewUser(user);
        return "redirect:/login";
    }
}
