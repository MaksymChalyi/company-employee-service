package com.maksimkaxxl.springbootrestfulapi.controllers;

import com.maksimkaxxl.springbootrestfulapi.services.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NewController {

    private final KafkaProducer kafkaProducer;

    @PostMapping("/kafka/send")
    public String send(@RequestBody String message) {
        kafkaProducer.sendMessage(message);

        return "Success";
    }
}
