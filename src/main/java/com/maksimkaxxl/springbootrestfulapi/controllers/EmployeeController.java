package com.maksimkaxxl.springbootrestfulapi.controllers;

import com.maksimkaxxl.springbootrestfulapi.dtos.EmployeeDto;
import com.maksimkaxxl.springbootrestfulapi.dtos.responce.EmployeeSummaryDto;
import com.maksimkaxxl.springbootrestfulapi.dtos.responce.UploadedEmployeeResponse;
import com.maksimkaxxl.springbootrestfulapi.services.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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

    @PostMapping("/_list")
    public ResponseEntity<?> getEmployeesByPage(@RequestBody(required = false) EmployeeSummaryDto employeeSummaryDto,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size) {

        Map<String, Object> response = employeeService.getEmployeesByPage(employeeSummaryDto, page, size);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/_report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> generateEmployeeReport(HttpServletResponse response, @RequestBody EmployeeSummaryDto employeeSummaryDto) {
        employeeService.generateEmployeeReport(response, employeeSummaryDto);
        return ResponseEntity.ok().build();
    }

}
