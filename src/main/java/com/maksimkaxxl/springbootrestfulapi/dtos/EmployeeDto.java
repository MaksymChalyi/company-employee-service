package com.maksimkaxxl.springbootrestfulapi.dtos;

import com.maksimkaxxl.springbootrestfulapi.annotations.AgeConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record EmployeeDto(

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Age is required")
        @AgeConstraint
        Integer age,

        @NotBlank(message = "Position is required")
        String position,

        @NotNull(message = "Experience years are required")
        @Positive(message = "Experience years must be a positive number")
        Integer experienceYears,

        List<String> interests,

        @NotNull(message = "Company is required")
        String companyName) {
}
