package project.group14.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.group14.authenticationservice.dto.PatientRecordDTO;
import project.group14.authenticationservice.dto.AppointmentDTO;
import project.group14.authenticationservice.dto.PatientRecordWithAppointmentsDTO;
import project.group14.authenticationservice.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ApplicationService applicationService;

    @Autowired
    public AdminController(ApplicationService  applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/patients")
    public List<PatientRecordDTO> getAllPatients() {
        return applicationService.getAllPatients();
    }

//    @GetMapping("/patients/{id}")
//    public PatientRecordDTO getPatientRecordById(@PathVariable Long id) {
//        return applicationService.getPatientRecordById(id);
//    }

    @GetMapping("/patients/{id}")
    public PatientRecordWithAppointmentsDTO getPatientRecordWithAppointmentsById(@PathVariable Long id) {
        return applicationService.getPatientRecordWithAppointmentsById(id);
    }

    @GetMapping("/appointments")
    public List<AppointmentDTO> getAllAppointments(@RequestParam(required = false) String date) {
        return applicationService.getAllAppointments(date);
    }

    @PutMapping("/appointments/{id}")
    public AppointmentDTO updateOrCancelAppointment(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO) {
        return applicationService.updateAppointment(id, appointmentDTO);
    }

    @GetMapping("/notifications")
    public List<String> getAllNotifications() {
        return applicationService.getAllNotifications();
    }

//    @PostMapping("/notifications/send")
//    public String sendNotification(@RequestBody String message) {
//        return applicationService.sendNotification(message);
//    }
//
//    @PostMapping("/notifications/daily")
//    public String sendDailyNotifications() {
//        return applicationService.sendDailyNotifications();
//    }
}
