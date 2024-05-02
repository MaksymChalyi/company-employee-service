package com.maksimkaxxl.springbootrestfulapi.services;

import com.maksimkaxxl.springbootrestfulapi.dtos.EmployeeDto;
import com.maksimkaxxl.springbootrestfulapi.dtos.responce.UploadedEmployeeResponse;
import com.maksimkaxxl.springbootrestfulapi.entities.Employee;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
    Employee createEmployee(EmployeeDto employeeDto);

    Employee findById(Long id);

    Employee updateEmployee(Long id, EmployeeDto employeeDto);

    void deleteEmployee(Long id);

    UploadedEmployeeResponse uploadEmployeesFromFile(MultipartFile file);


}
