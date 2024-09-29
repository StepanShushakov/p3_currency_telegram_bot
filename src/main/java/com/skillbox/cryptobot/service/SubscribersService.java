package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.Subscribers;
import com.skillbox.cryptobot.exception.SubscriberAlreadyExist;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscribersService {

    private final SubscribersRepository subscribersRepository;

    private Subscribers getSubscribers(Long chatId) {
        return subscribersRepository.findById(chatId)
                .orElseThrow();
    }

    public void addNewSubscriber(Long id) {
        Optional<Subscribers> optionalSubscriber = subscribersRepository.findById(id);
        if (optionalSubscriber.isPresent()) {
            throw new SubscriberAlreadyExist("Подписчик уже зарегистрирован");
        }
        Subscribers  subscriber = new Subscribers();
        subscriber.setId(id);
        subscribersRepository.save(subscriber);
    }

    public String getStringResultOfSubscribeInstallation(Long chatId, Double price) {
        Subscribers subscriber = getSubscribers(chatId);
        subscriber.setPrice(price);
        subscriber.setLastNotification(LocalDateTime.now());
        subscribersRepository.save(subscriber);
        return "Новая подписка создана на стоимость " + price;
    }

    public void unsubscribe(Long chatId) {
        Subscribers subscribers = getSubscribers(chatId);
        subscribers.setPrice(null);
        subscribers.setLastNotification(null);
        subscribersRepository.save(subscribers);
    }

    public String getSubscription(Long chatId) {
        Subscribers subscribers = getSubscribers(chatId);
        return (subscribers.getPrice() == null
                ? "Активные подписки отсутствуют"
                : "Вы подписаны на стоимость биткоина " + subscribers.getPrice() + " USD");
    }
}
