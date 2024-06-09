package com.maksimkaxxl.springbootrestfulapi.services;

public interface KafkaProducer {

    void sendMessage(String message);
}
