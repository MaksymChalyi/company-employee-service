package com.maksimkaxxl.springbootrestfulapi.controllers;

import com.maksimkaxxl.springbootrestfulapi.dtos.CompanyDto;
import com.maksimkaxxl.springbootrestfulapi.entities.Company;
import com.maksimkaxxl.springbootrestfulapi.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody CompanyDto companyDto) {
        var createdCompany = companyService.createCompany(companyDto);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        var companies = companyService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }


}
