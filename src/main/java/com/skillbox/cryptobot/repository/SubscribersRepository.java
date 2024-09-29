package com.skillbox.cryptobot.repository;

import com.skillbox.cryptobot.entity.Subscribers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribersRepository extends JpaRepository<Subscribers, Long> {
}
