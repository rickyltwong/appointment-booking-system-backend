package project.group14.medicalrecordservice.service;

import project.group14.medicalrecordservice.dto.AppointmentDTO;
import project.group14.medicalrecordservice.repository.PatientRecordRepository;
import project.group14.medicalrecordservice.model.PatientRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class PatientRecordService {

    private final PatientRecordRepository patientRecordRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public PatientRecordService(PatientRecordRepository patientRecordRepository, RestTemplate restTemplate) {
        this.patientRecordRepository = patientRecordRepository;
        this.restTemplate = restTemplate;
    }

    private static final String APPOINTMENT_SERVICE_URL = "http://localhost:8081/appointments";

    public List<PatientRecord> getAllPatients() {
        return patientRecordRepository.findAll();
    }

    public Optional<PatientRecord> getPatientById(Long id) {
        return patientRecordRepository.findById(id);
    }

    public PatientRecord savePatientRecord(PatientRecord patientRecord) {
        return patientRecordRepository.save(patientRecord);
    }

    public PatientRecord updatePatientRecord(Long id, PatientRecord patientRecordDetails) {
        Optional<PatientRecord> optionalPatientRecord = patientRecordRepository.findById(id);
        if (optionalPatientRecord.isPresent()) {
            PatientRecord existingPatientRecord = optionalPatientRecord.get();
            existingPatientRecord.setFirstName(patientRecordDetails.getFirstName());
            existingPatientRecord.setLastName(patientRecordDetails.getLastName());
            existingPatientRecord.setGender(patientRecordDetails.getGender());
            existingPatientRecord.setAddress(patientRecordDetails.getAddress());
            existingPatientRecord.setPhoneNumber(patientRecordDetails.getPhoneNumber());
            existingPatientRecord.setEmail(patientRecordDetails.getEmail());
            existingPatientRecord.setDateOfBirth(patientRecordDetails.getDateOfBirth());
            return patientRecordRepository.save(existingPatientRecord);
        } else {
            throw new RuntimeException("Patient not found");
        }
    }

    public void deletePatientRecord(Long id) {
        patientRecordRepository.deleteById(id);
    }

    public List getAppointmentsForPatient(Long patientRecordId) {
        String url = APPOINTMENT_SERVICE_URL + "?patientRecordId=" + patientRecordId;
        return restTemplate.getForObject(url, List.class);
    }

    public AppointmentDTO createAppointmentForPatient(AppointmentDTO appointmentDTO) {
        return restTemplate.postForObject(APPOINTMENT_SERVICE_URL + "/new", appointmentDTO, AppointmentDTO.class);
    }

    public AppointmentDTO updateAppointmentForPatient(Long appointmentId, AppointmentDTO appointmentDTO) {
        restTemplate.put(APPOINTMENT_SERVICE_URL + "/" + appointmentId, appointmentDTO);
        return appointmentDTO;
    }

    public void deleteAppointmentForPatient(Long appointmentId) {
        restTemplate.delete(APPOINTMENT_SERVICE_URL + "/" + appointmentId);
    }
}
