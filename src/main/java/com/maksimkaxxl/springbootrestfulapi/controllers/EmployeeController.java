package com.maksimkaxxl.springbootrestfulapi.controllers;

import com.maksimkaxxl.springbootrestfulapi.dtos.CompanyDto;
import com.maksimkaxxl.springbootrestfulapi.dtos.EmployeeDto;
import com.maksimkaxxl.springbootrestfulapi.dtos.responce.UploadedEmployeeResponse;
import com.maksimkaxxl.springbootrestfulapi.entities.Employee;
import com.maksimkaxxl.springbootrestfulapi.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {


    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        var createdEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findEmployeeById(@PathVariable Long id) {
        var employee = employeeService.findById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employeeDto) {

        var updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.ok().body(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/upload")
    public ResponseEntity<UploadedEmployeeResponse> uploadEmployees(@RequestParam("file") MultipartFile file) {
        UploadedEmployeeResponse statisticsOfUploads = employeeService.uploadEmployeesFromFile(file);
        return new ResponseEntity<>(statisticsOfUploads, HttpStatus.CREATED);

    }


}
