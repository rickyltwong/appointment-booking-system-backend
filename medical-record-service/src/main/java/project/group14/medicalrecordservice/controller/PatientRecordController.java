package project.group14.medicalrecordservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.group14.medicalrecordservice.dto.PatientRecordDTO;
import project.group14.medicalrecordservice.service.PatientRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientRecordController {

    private final PatientRecordService patientRecordService;

    @Autowired
    public PatientRecordController(PatientRecordService patientRecordService) {
        this.patientRecordService = patientRecordService;
    }

    @GetMapping
    public List<PatientRecordDTO> getAllPatients() {
        return patientRecordService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientRecordDTO> getPatientById(@PathVariable Long id) {
        Optional<PatientRecordDTO> patientRecordDTO = patientRecordService.getPatientById(id);
        return patientRecordDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<PatientRecordDTO> getPatientByUserId(@PathVariable Long userId) {
        Optional<PatientRecordDTO> patientRecordDTO = patientRecordService.getPatientById(userId);
        return patientRecordDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<PatientRecordDTO> createPatientRecord(@RequestBody PatientRecordDTO patientRecordDTO) {
        PatientRecordDTO savedRecord = patientRecordService.savePatientRecord(patientRecordDTO);
        return ResponseEntity.ok(savedRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientRecordDTO> updatePatientRecord(@PathVariable Long id, @RequestBody PatientRecordDTO patientRecordDetails) {
        try {
            PatientRecordDTO updatedRecord = patientRecordService.updatePatientRecord(id, patientRecordDetails);
            return ResponseEntity.ok(updatedRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientRecord(@PathVariable Long id) {
        try {
            patientRecordService.deletePatientRecord(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
