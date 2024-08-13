package project.group14.medicalrecordservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {

    private Long id;
    private String status;

    private LocalDateTime appointmentDateTime;
    private Integer appointmentDuration;

    private String doctorName;
    private String clinicalNotes;

    private Long patientRecordId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
