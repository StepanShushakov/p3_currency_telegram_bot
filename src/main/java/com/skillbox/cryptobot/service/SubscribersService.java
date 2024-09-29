package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.Subscribers;
import com.skillbox.cryptobot.exception.SubscriberAlreadyExist;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscribersService {

    private final SubscribersRepository subscribersRepository;

    public void addNewSubscriber(Long id) {
        Optional<Subscribers> optionalSubscriber = subscribersRepository.findById(id);
        if (optionalSubscriber.isPresent()) {
            throw new SubscriberAlreadyExist("Подписчик уже зарегистрирован");
        }
        Subscribers  subscriber = new Subscribers();
        subscriber.setId(id);
        subscriber.setUuid(UUID.randomUUID());
        subscribersRepository.save(subscriber);
    }

    public String getStringResultOfSubscribeInstallation(Long chatId, Double price) {
        Subscribers subscriber = subscribersRepository.findById(chatId)
                .orElseThrow();
        subscriber.setPrice(price);
        subscribersRepository.save(subscriber);
        return "Новая подписка создана на стоимость " + price;
    }
}
