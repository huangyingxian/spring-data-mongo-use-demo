package com.example.demo.service;

import com.example.demo.domain.entity.Company;
import com.example.demo.domain.entity.Department;
import com.example.demo.domain.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class PipelineService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void initData(){

        // 公司
        Company company = new Company();
        company.setCompanyName("XXX公司");
        company.setMobile("023-66668888");
        mongoTemplate.save(company);

        // 部门
        Department department = new Department();
        department.setDepartmentName("XXX信息开发系统");
        department.setCompany(company);
        department.setEmployeeList(Collections.emptyList());
        mongoTemplate.save(department);

        // 员工
        List<Employee> employeeList = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setEmployeeName("张一");
        employee1.setPhone("159228359xx");
        employee1.setDepartment(department);
        employeeList.add(employee1);

        Employee employee2 = new Employee();
        employee2.setEmployeeName("张二");
        employee2.setPhone("159228358xx");
        employee2.setDepartment(department);
        employeeList.add(employee2);
        mongoTemplate.insert(employeeList, Employee.class);

        department.setEmployeeList(employeeList);
        mongoTemplate.save(department);
    }

}
