package com.maksimkaxxl.springbootrestfulapi.services.impl;

import com.maksimkaxxl.springbootrestfulapi.dtos.EmployeeDto;
import com.maksimkaxxl.springbootrestfulapi.entities.Company;
import com.maksimkaxxl.springbootrestfulapi.entities.Employee;
import com.maksimkaxxl.springbootrestfulapi.exceptions.CompanyNotFoundException;
import com.maksimkaxxl.springbootrestfulapi.repository.CompanyRepository;
import com.maksimkaxxl.springbootrestfulapi.repository.EmployeeRepository;
import com.maksimkaxxl.springbootrestfulapi.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;


    @Override
    public Employee createEmployee(EmployeeDto employeeDto) {

        Company existingCompany = companyRepository.findByName(employeeDto.companyName())
                .orElseThrow(() -> new CompanyNotFoundException("Company with name " + employeeDto.companyName() + " not found"));

        Employee employee = new Employee();
        employee.setName(employeeDto.name());
        employee.setAge(employeeDto.age());
        employee.setPosition(employeeDto.position());
        employee.setExperienceYears(employeeDto.experienceYears());
        employee.setInterests(employeeDto.interests());
        employee.setCompany(existingCompany);
        return employeeRepository.save(employee);
    }
}
