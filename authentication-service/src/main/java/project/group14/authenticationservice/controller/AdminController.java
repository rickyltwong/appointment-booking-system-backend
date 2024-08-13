package project.group14.authenticationservice.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/test")
    public String getAdminHome() {
        return "Welcome to the admin home page!";
    }
}
