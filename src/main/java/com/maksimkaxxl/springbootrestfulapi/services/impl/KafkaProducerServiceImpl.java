package com.maksimkaxxl.springbootrestfulapi.services.impl;

import com.maksimkaxxl.springbootrestfulapi.dtos.EmployeeDto;
import com.maksimkaxxl.springbootrestfulapi.services.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Value("${kafka.topic.mailSender}")
    private String mailSenderTopic;

    private final KafkaOperations<String, EmployeeDto> kafkaOperations;

    @Override
    public void sendMessage(EmployeeDto employeeDto) {
        kafkaOperations.send(mailSenderTopic, employeeDto);
    }

}
