package com.maksimkaxxl.springbootrestfulapi.dtos;

import com.maksimkaxxl.springbootrestfulapi.annotations.AgeConstraint;
import com.maksimkaxxl.springbootrestfulapi.entities.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Age is required")
    @Positive(message = "Age must be a positive number")
    @AgeConstraint
    private Integer age;

    @NotBlank(message = "Position is required")
    private String position;

    @NotNull(message = "Experience years are required")
    @Positive(message = "Experience years must be a positive number")
    private Integer experienceYears;

    private List<String> interests;

    @NotNull(message = "Company is required")
    private Company company;
}
