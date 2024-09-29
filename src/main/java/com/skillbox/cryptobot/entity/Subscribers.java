package com.skillbox.cryptobot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Subscribers {

    @Id
    private Long id;
    private UUID uuid;
    private Double price;

}
