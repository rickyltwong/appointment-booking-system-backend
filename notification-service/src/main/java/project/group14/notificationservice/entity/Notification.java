package project.group14.notificationservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.flogger.Flogger;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project.group14.notificationservice.domain.NotificationStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    private Long patientId;
    private Long appointmentId;
    private LocalDateTime appointmentDate;
    private NotificationStatus status;
    private NotificationStatus reminderStatus;

    public Notification(Long id, String title, String message, Long patientId, Long appointmentId, LocalDateTime appointmentDate) {
        this.title = title;
        this.id = id;
        this.message = message;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
    }

    @CreationTimestamp
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updatedDate;
}