package com.maksimkaxxl.springbootrestfulapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "age")
    Integer age;

    @Column(name = "position")
    String position;

    @Column(name = "experienceYears")
    Integer experienceYears;

    @Column(name = "interests")
    String interests;

    @ManyToOne
    @JoinColumn(name = "id")
    private Company company;



}
