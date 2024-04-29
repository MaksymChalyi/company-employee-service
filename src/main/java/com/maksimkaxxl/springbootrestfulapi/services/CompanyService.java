package com.maksimkaxxl.springbootrestfulapi.services;

import com.maksimkaxxl.springbootrestfulapi.dtos.CompanyDto;
import com.maksimkaxxl.springbootrestfulapi.entities.Company;

public interface CompanyService {

    Company createCompany(CompanyDto companyDto);
}
