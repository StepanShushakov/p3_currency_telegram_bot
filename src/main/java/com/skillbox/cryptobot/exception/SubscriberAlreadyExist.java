package com.skillbox.cryptobot.exception;

public class SubscriberAlreadyExist extends RuntimeException{
    public SubscriberAlreadyExist(String message) {
        super(message);
    }
}
