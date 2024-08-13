package project.group14.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.group14.notificationservice.domain.NotificationStatus;
import project.group14.notificationservice.entity.Notification;
import project.group14.notificationservice.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
public class NotificationScheduler {

    private final NotificationRepository notificationRepository;

    public NotificationScheduler(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Job to run every day 12.00 AM
    @Scheduled(cron = "0 0 0 * * ?")
    public void runDailyJob() {
        log.info("Daily job running at 12:00 AM...");
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        List<Notification> notifications = notificationRepository.findNotificationByAppointmentDateBetween(startOfDay, endOfDay);
        for (Notification notification : notifications) {
            if (notification.getReminderStatus() == NotificationStatus.CREATED) {
                try {
                    log.info("Reminder Notification successfully sent : {}", notification);
                    notification.setReminderStatus(NotificationStatus.SENT);

                } catch (Exception e) {
                    log.error(e.getMessage());
                    notification.setReminderStatus(NotificationStatus.FAILED);
                }
                notificationRepository.save(notification);
            }
        }
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void runEveryFiveMinutesJob() {
        log.info("Job running every 5 minutes...");
        List<Notification> notifications = notificationRepository.findNotificationByStatus(NotificationStatus.CREATED);
        for (Notification notification : notifications) {
            if (notification.getStatus() == NotificationStatus.CREATED) {
                try {
                    log.info("Notification successfully sent : {}", notification);
                    notification.setStatus(NotificationStatus.SENT);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    notification.setStatus(NotificationStatus.FAILED);
                }
                notificationRepository.save(notification);
            }
        }
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void runEveryMinuteJob() {
        log.info("Job running every 1 minute...");
        long count = notificationRepository.count();
        log.info("Total number of records in Notification Database: {}", count);
    }
}
