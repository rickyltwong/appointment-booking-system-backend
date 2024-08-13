package project.group14.authenticationservice.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.group14.authenticationservice.model.User;
import project.group14.authenticationservice.service.AuthService;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") User user) {
        authService.saveUser(user);
        return "redirect:/login";
    }
}
