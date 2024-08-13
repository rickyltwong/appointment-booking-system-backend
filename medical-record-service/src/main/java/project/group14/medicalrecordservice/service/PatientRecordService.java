package project.group14.medicalrecordservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.group14.medicalrecordservice.dto.PatientRecordDTO;
import project.group14.medicalrecordservice.model.PatientRecord;
import project.group14.medicalrecordservice.repository.PatientRecordRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientRecordService {

    private final PatientRecordRepository patientRecordRepository;

    @Autowired
    public PatientRecordService(PatientRecordRepository patientRecordRepository) {
        this.patientRecordRepository = patientRecordRepository;
    }

    public List<PatientRecordDTO> getAllPatients() {
        return patientRecordRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PatientRecordDTO> getPatientById(Long id) {
        return patientRecordRepository.findById(id)
                .map(this::convertToDTO);
    }

    public PatientRecordDTO savePatientRecord(PatientRecordDTO patientRecordDTO) {
        PatientRecord patientRecord = convertToEntity(patientRecordDTO);
        PatientRecord savedRecord = patientRecordRepository.save(patientRecord);
        return convertToDTO(savedRecord);
    }

    public PatientRecordDTO updatePatientRecord(Long id, PatientRecordDTO patientRecordDetails) {
        Optional<PatientRecord> optionalPatientRecord = patientRecordRepository.findById(id);

        if (!optionalPatientRecord.isPresent()) {
            // If not found by ID, try to find by UserId
            optionalPatientRecord = Optional.ofNullable(patientRecordRepository.findByUserId(id));
        }

        if (optionalPatientRecord.isPresent()) {
            PatientRecord existingPatientRecord = optionalPatientRecord.get();
            existingPatientRecord.setFirstName(patientRecordDetails.getFirstName());
            existingPatientRecord.setLastName(patientRecordDetails.getLastName());
            existingPatientRecord.setGender(patientRecordDetails.getGender());
            existingPatientRecord.setAddress(patientRecordDetails.getAddress());
            existingPatientRecord.setPhoneNumber(patientRecordDetails.getPhoneNumber());
            existingPatientRecord.setEmail(patientRecordDetails.getEmail());
            existingPatientRecord.setDateOfBirth(patientRecordDetails.getDateOfBirth());
            PatientRecord updatedRecord = patientRecordRepository.save(existingPatientRecord);
            return convertToDTO(updatedRecord);
        } else {
            throw new RuntimeException("Patient not found");
        }
    }

    public void deletePatientRecord(Long id) {
        patientRecordRepository.deleteById(id);
    }

    private PatientRecordDTO convertToDTO(PatientRecord patientRecord) {
        return new PatientRecordDTO(
                patientRecord.getId(),
                patientRecord.getUserId(),
                patientRecord.getFirstName(),
                patientRecord.getLastName(),
                patientRecord.getGender(),
                patientRecord.getAddress(),
                patientRecord.getPhoneNumber(),
                patientRecord.getEmail(),
                patientRecord.getDateOfBirth(),
                patientRecord.getCreatedAt()
        );
    }

    private PatientRecord convertToEntity(PatientRecordDTO patientRecordDTO) {
        return new PatientRecord(
                patientRecordDTO.getId(),
                patientRecordDTO.getUserId(),
                patientRecordDTO.getFirstName(),
                patientRecordDTO.getLastName(),
                patientRecordDTO.getGender(),
                patientRecordDTO.getAddress(),
                patientRecordDTO.getPhoneNumber(),
                patientRecordDTO.getEmail(),
                patientRecordDTO.getDateOfBirth(),
                patientRecordDTO.getCreatedAt()
        );
    }
}
