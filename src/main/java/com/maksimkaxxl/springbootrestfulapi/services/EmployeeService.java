package com.maksimkaxxl.springbootrestfulapi.services;

import com.maksimkaxxl.springbootrestfulapi.dtos.EmployeeDto;
import com.maksimkaxxl.springbootrestfulapi.entities.Employee;

public interface EmployeeService {
    Employee createEmployee(EmployeeDto employeeDto);

    Employee findById(Long id);

    Employee updateEmployee(Long id, EmployeeDto employeeDto);
}
