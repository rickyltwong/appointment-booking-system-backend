package project.group14.authenticationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRecordWithAppointmentsDTO extends PatientRecordDTO {

    private List<AppointmentDTO> appointments;

    public PatientRecordWithAppointmentsDTO(PatientRecordDTO patientRecordDTO, List<AppointmentDTO> appointments) {
        super(
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
        this.appointments = appointments;
    }
}
