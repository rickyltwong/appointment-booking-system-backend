package project.group14.appointmentservice.controller;

import project.group14.appointmentservice.dto.AppointmentDTO;
import project.group14.appointmentservice.repository.AppointmentRepository;
import project.group14.appointmentservice.service.AppointmentService;
import project.group14.medicalrecordservice.model.PatientRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<AppointmentDTO> getAppointmentsByPatientRecord(@RequestParam Long patientRecordId) {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(patientRecordId);
        return appointmentService.getAppointmentsByPatientRecord(patientRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        Optional<AppointmentDTO> appointmentDTO = appointmentService.getAppointmentById(id);
        return appointmentDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public AppointmentDTO createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.saveAppointment(appointmentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, appointmentDTO));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
//        appointmentService.deleteAppointment(id);
//        return ResponseEntity.noContent().build();
//    }
}
