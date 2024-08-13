package project.group14.authenticationservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.group14.authenticationservice.dto.UserDTO;
import project.group14.authenticationservice.model.User;
import project.group14.authenticationservice.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<String> registerUserAccount(@RequestBody UserDTO user) {
        authService.saveUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/admin/register")
    public ResponseEntity<String> registerAdminAccount(@RequestBody UserDTO user) {
        authService.saveAdmin(user);
        return ResponseEntity.ok("Admin created successfully");
    }

}
