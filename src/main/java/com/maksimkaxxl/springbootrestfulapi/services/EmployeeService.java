package com.maksimkaxxl.springbootrestfulapi.services;

import com.maksimkaxxl.springbootrestfulapi.dtos.EmployeeDto;
import com.maksimkaxxl.springbootrestfulapi.entities.Employee;

public interface EmployeeService {
    Employee createEmployee(EmployeeDto employeeDto);
}
