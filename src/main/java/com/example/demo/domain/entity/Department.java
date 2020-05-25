package com.example.demo.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
public class Department {
    @Id
    private String id;

    private String departmentName;

    @DBRef
    private Company company;

    @DBRef
    private List<Employee> employeeList;
}
