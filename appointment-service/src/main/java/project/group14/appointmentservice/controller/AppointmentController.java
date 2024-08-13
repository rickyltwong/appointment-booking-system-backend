package project.group14.appointmentservice.controller;

import project.group14.appointmentservice.dto.AppointmentDTO;
import project.group14.appointmentservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<AppointmentDTO> getAppointments(
            @RequestParam(required = false) Long patientRecordId,
            @RequestParam(required = false) String date) {
        if (patientRecordId != null) {
            return appointmentService.getAppointmentsByPatientRecordId(patientRecordId);
        } else if (date != null) {
            LocalDate localDate = LocalDate.parse(date);
            return appointmentService.getAppointmentsByDate(localDate);
        } else {
            return appointmentService.getAllAppointments(); // NOTE: or choose to throw an error
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        Optional<AppointmentDTO> appointmentDTO = appointmentService.getAppointmentById(id);
        return appointmentDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AppointmentDTO createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.saveAppointment(appointmentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, appointmentDTO));
    }

    @GetMapping("/admin/all")
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
}
