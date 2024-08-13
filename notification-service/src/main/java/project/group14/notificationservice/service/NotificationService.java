package project.group14.notificationservice.service;

import org.springframework.stereotype.Service;
import project.group14.notificationservice.domain.NotificationStatus;
import project.group14.notificationservice.dto.NotificationDTO;
import project.group14.notificationservice.entity.Notification;
import project.group14.notificationservice.repository.NotificationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification saveNotification(NotificationDTO notificationDTO) {
        Notification oldNotification = notificationRepository.findNotificationByAppointmentId(notificationDTO.getAppointmentId());
        if (oldNotification != null) {
            notificationRepository.delete(oldNotification);
        }
        Notification notification = convertToNotificationEntity(notificationDTO);
        notification.setStatus(NotificationStatus.CREATED);
        notification.setReminderStatus(NotificationStatus.CREATED);
        return notificationRepository.save(notification);
    }


    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getAllPatientNotifications(Long patientRecId) {
        return notificationRepository.findNotificationByPatientId(patientRecId);
    }


    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public Notification updateNotification(Long id, Notification notification) {
        notification.setId(id);
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    public static Notification convertToNotificationEntity(NotificationDTO notificationDTO) {
        return new Notification(
                notificationDTO.getId(),
                notificationDTO.getTitle(),
                notificationDTO.getMessage(),
                notificationDTO.getPatientId(),
                notificationDTO.getAppointmentId(),
                notificationDTO.getAppointmentDate()
        );
    }
}
