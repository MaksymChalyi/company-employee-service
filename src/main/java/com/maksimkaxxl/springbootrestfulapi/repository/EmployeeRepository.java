package com.maksimkaxxl.springbootrestfulapi.repository;

import com.maksimkaxxl.springbootrestfulapi.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
