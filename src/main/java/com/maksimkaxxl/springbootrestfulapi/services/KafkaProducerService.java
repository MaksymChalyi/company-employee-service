package com.maksimkaxxl.springbootrestfulapi.services;

import com.maksimkaxxl.springbootrestfulapi.dtos.EmployeeDto;

public interface KafkaProducerService {

    void sendMessage(EmployeeDto employeeDto);
}
