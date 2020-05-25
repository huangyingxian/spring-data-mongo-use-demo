package com.example.demo.controller;

import com.example.demo.service.MultiDocument;
import com.example.demo.service.PipelineService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("pipeline")
public class PipelineController {

    @Autowired
    private PipelineService pipelineService;

    @Autowired
    private MultiDocument multiDocument;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String initData() {
        pipelineService.initData();
        return "初始化数据完成";
    }

    @RequestMapping(value = "/twotable", method = RequestMethod.GET)
    public String twoTable() {
        String employeeAndDepartmentString = multiDocument.twoTableQuery();
        return employeeAndDepartmentString;
    }

    @RequestMapping(value = "/threetable", method = RequestMethod.GET)
    public String threeTable() {
        String employeeAndDepartmentAndCompanyString = multiDocument.threeTableQuery();
        return employeeAndDepartmentAndCompanyString;
    }

    @RequestMapping(value = "/onetomany", method = RequestMethod.GET)
    public String oneToMany() {
        String departmentAndEmployeeAndString = multiDocument.oneToManyTableQuery();
        return departmentAndEmployeeAndString;
    }
}
