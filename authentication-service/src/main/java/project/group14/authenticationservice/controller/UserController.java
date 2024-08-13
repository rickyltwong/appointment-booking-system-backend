package project.group14.authenticationservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.group14.authenticationservice.model.User;
import project.group14.authenticationservice.service.ApplicationService;
import project.group14.authenticationservice.dto.AppointmentDTO;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {


    private final ApplicationService userService;

    @Autowired
    public UserController(ApplicationService userService) {
        this.userService = userService;
    }

}
