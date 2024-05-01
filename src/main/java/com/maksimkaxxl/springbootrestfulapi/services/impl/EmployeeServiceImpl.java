package com.maksimkaxxl.springbootrestfulapi.services.impl;

import com.maksimkaxxl.springbootrestfulapi.dtos.EmployeeDto;
import com.maksimkaxxl.springbootrestfulapi.entities.Company;
import com.maksimkaxxl.springbootrestfulapi.entities.Employee;
import com.maksimkaxxl.springbootrestfulapi.exceptions.CompanyNotFoundException;
import com.maksimkaxxl.springbootrestfulapi.exceptions.EmployeeNotFoundException;
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
        Company existingCompany = getExistingCompany(employeeDto.companyName());
        Employee employee = mapEmployeeDtoToEntity(employeeDto, existingCompany);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee existingEmployee = getExistingEmployee(id);
        Company existingCompany = getExistingCompany(employeeDto.companyName());
        mapEmployeeDtoToEntity(employeeDto, existingCompany, existingEmployee);
        return employeeRepository.save(existingEmployee);
    }

    private Company getExistingCompany(String companyName) {
        return companyRepository.findByName(companyName)
                .orElseThrow(() -> new CompanyNotFoundException("Company with name " + companyName + " not found"));
    }

    private Employee getExistingEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));
    }

    private Employee mapEmployeeDtoToEntity(EmployeeDto employeeDto, Company company) {
        Employee employee = new Employee();
        return mapEmployeeDtoToEntity(employeeDto, company, employee);
    }

    private Employee mapEmployeeDtoToEntity(EmployeeDto employeeDto, Company company, Employee employee) {
        employee.setName(employeeDto.name());
        employee.setAge(employeeDto.age());
        employee.setPosition(employeeDto.position());
        employee.setExperienceYears(employeeDto.experienceYears());
        employee.setInterests(employeeDto.interests());
        employee.setCompany(company);
        return employee;
    }

}
