package project.group14.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.group14.notificationservice.domain.NotificationStatus;
import project.group14.notificationservice.entity.Notification;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findNotificationByStatus(NotificationStatus status);

    List<Notification> findNotificationByPatientId(Long patientId);

    Notification findNotificationByAppointmentId(Long appointmentId);

    List<Notification> findNotificationByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}