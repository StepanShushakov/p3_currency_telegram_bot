package com.skillbox.cryptobot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Subscribers {

    @Id
    private Long id;
    private Double price;
    private LocalDateTime lastNotification;

}
