package com.skillbox.cryptobot.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NotificationScheduler {

    private final NotificationService notificationService;

    public NotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRateString = "${telegram.bot.price.check.frequency.ms}")
    public void checkPriceAndNotify() throws IOException {
        notificationService.sendNotifications();
    }
}
