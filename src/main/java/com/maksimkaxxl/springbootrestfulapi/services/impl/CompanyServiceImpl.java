package com.maksimkaxxl.springbootrestfulapi.services.impl;

import com.maksimkaxxl.springbootrestfulapi.dtos.CompanyDto;
import com.maksimkaxxl.springbootrestfulapi.entities.Company;
import com.maksimkaxxl.springbootrestfulapi.exceptions.CompanyNotFoundException;
import com.maksimkaxxl.springbootrestfulapi.exceptions.DuplicateCompanyNameException;
import com.maksimkaxxl.springbootrestfulapi.repository.CompanyRepository;
import com.maksimkaxxl.springbootrestfulapi.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company createCompany(CompanyDto companyDto) {
        if (companyRepository.findByName(companyDto.name()).isPresent()) {
            throw new DuplicateCompanyNameException("Duplicate company name found: " + companyDto.name());
        }
        Company company = new Company();
        company.setName(companyDto.name());
        company.setIndustry(companyDto.industry());
        return companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void deleteCompanyById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            companyRepository.deleteById(id);
        } else {
            throw new CompanyNotFoundException("Company with ID " + id + " not found");

        }


    }
}
