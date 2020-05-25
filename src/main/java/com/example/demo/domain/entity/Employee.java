package com.example.demo.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class Employee {
    @Id
    private String id;

    private String employeeName;

    private String phone;

    @DBRef
    private Department department;
}
