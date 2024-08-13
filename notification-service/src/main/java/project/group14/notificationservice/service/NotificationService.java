package project.group14.notificationservice.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    // Job to run every day 12.00 AM
    @Scheduled(cron = "0 0 0 * * ?")
    public void runDailyJob() {
        System.out.println("Daily job running at 12:00 AM...");
    }

    // Job to run every 5 minutes
    @Scheduled(cron = "0 */5 * * * ?")
    public void runEveryFiveMinutesJob() {
        System.out.println("Job running every 5 minutes...");
    }
}
