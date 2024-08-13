package project.group14.authenticationservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.group14.authenticationservice.dto.AppointmentDTO;
import project.group14.authenticationservice.dto.PatientRecordDTO;
import project.group14.authenticationservice.model.User;
import project.group14.authenticationservice.service.ApplicationService;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private final ApplicationService userService;

    @Autowired
    public UserController(ApplicationService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String testUserAccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            System.out.println("User: " + user.getUsername() + ", Roles: " + user.getAuthorities());
            return "User ID: " + user.getId() + ", Roles: " + user.getAuthorities();
        }
        return "User not authenticated";
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDTO>> getUserAppointments() {
        return ResponseEntity.ok(userService.getUserAppointments());
    }

    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentDTO> getUserAppointmentById(@PathVariable Long appointmentId) {
        AppointmentDTO appointmentDTO = userService.getUserAppointment(appointmentId);

        if (appointmentDTO != null) {
            return ResponseEntity.ok(appointmentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentDTO> createUserAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.ok(userService.createAppointment(appointmentDTO));
    }

    @PutMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentDTO> updateUserAppointment(@PathVariable Long appointmentId, @RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.ok(userService.updateUserAppointment(appointmentId, appointmentDTO));
    }

    @PostMapping("/patients")
    public ResponseEntity<PatientRecordDTO> createUserPatientRecord(@RequestBody PatientRecordDTO patientRecordDTO) {
        return ResponseEntity.ok(userService.createPatientRecord(patientRecordDTO));
    }


    @GetMapping("/patients")
    public ResponseEntity<PatientRecordDTO> getUserPatientRecord() {
        return ResponseEntity.ok(userService.getPatientRecordByUserId());
    }

//
//    @DeleteMapping("/{userId}/appointments/{appointmentId}")
//    public ResponseEntity<Void> cancelUserAppointment(@PathVariable Long userId, @PathVariable Long appointmentId) {
//        appointmentService.cancelAppointmentByUser(userId, appointmentId);
//        return ResponseEntity.noContent().build();
//    }
}
