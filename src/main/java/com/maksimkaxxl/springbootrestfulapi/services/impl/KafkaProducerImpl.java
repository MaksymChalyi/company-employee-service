package com.maksimkaxxl.springbootrestfulapi.services.impl;

import com.maksimkaxxl.springbootrestfulapi.services.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {

    public final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String message){
        kafkaTemplate.send("mailSender",message);
    }

}
