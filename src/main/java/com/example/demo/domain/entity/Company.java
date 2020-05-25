package com.example.demo.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Company {
    @Id
    private String id;

    private String companyName;

    private String mobile;
}
