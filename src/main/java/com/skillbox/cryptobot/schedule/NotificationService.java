package com.skillbox.cryptobot.schedule;

import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.entity.Subscribers;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class NotificationService {

    private final SubscribersRepository subscribersRepository;
    private final CryptoCurrencyService cryptoCurrencyService;
    private final CryptoBot cryptoBot;

    public NotificationService(SubscribersRepository subscribersRepository, CryptoCurrencyService cryptoCurrencyService, CryptoBot cryptoBot) {
        this.subscribersRepository = subscribersRepository;
        this.cryptoCurrencyService = cryptoCurrencyService;
        this.cryptoBot = cryptoBot;
    }

    @Value("${telegram.bot.notify.frequency.minutes}")
    private int notificationFrequencyMinutes;

    public void sendNotifications() throws IOException {
        double currentPrice = cryptoCurrencyService.getBitcoinPrice();
        List<Subscribers> subscribers = subscribersRepository.findAllByPriceGreaterThan(currentPrice);

        for (Subscribers subscriber : subscribers) {
            if (shouldSendNotification(subscriber)) {
                sendMessage(subscriber, currentPrice);
                subscriber.setLastNotification(LocalDateTime.now());
                subscribersRepository.save(subscriber);
            }
        }
    }

    private boolean shouldSendNotification(Subscribers subscriber) {
        LocalDateTime lastNotification = subscriber.getLastNotification();
        return lastNotification == null ||
                lastNotification.plusMinutes(notificationFrequencyMinutes).isBefore(LocalDateTime.now());
    }

    private void sendMessage(Subscribers subscriber, double currentPrice) {
        try {
            cryptoBot.execute(SendMessage.builder()
                    .chatId(subscriber.getId())
                    .text(String.format("Пора покупать, стоимость биткоина %.2f", currentPrice))
                    .build());
        } catch (TelegramApiException e) {
            log.error("Error occurred in notification service", e);
        }
    }
}
