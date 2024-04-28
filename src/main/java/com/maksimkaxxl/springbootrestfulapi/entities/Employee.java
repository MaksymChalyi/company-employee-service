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
    private Integer age;

    @Column(name = "position")
    private String position;

    @Column(name = "experienceYears")
    private Integer experienceYears;

    @ElementCollection
    private List<String> interests;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


}
