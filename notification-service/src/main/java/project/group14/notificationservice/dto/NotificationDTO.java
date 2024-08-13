package project.group14.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDTO {
    private Long id;
    private String title;
    private String message;
    private Long patientId;
    private Long appointmentId;
    private LocalDateTime appointmentDate;
}
