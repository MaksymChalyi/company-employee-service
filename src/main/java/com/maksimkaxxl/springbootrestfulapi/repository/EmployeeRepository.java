package com.maksimkaxxl.springbootrestfulapi.repository;

import com.maksimkaxxl.springbootrestfulapi.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByName(String name);

    Optional<Employee> findById(Long id);
}
