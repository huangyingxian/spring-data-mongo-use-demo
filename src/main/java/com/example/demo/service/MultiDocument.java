package com.example.demo.service;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.domain.entity.Employee;
import com.example.demo.util.RemoveDollarOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MultiDocument {

    @Autowired
    private MongoTemplate mongoTemplate;

    public String twoTableQuery() {
        // 1、消除@DBRef引用对象中的"$id"的"$"符号
        RemoveDollarOperation removeDollarOperation = new RemoveDollarOperation("newDepartmentFieldName", "department");

        // 2、使用mongodb $lookup实现左连接部门表
        LookupOperation lookupOperation = LookupOperation.newLookup().from("department")
                .localField("newDepartmentFieldName.id").foreignField("_id").as("newDepartment");

        // $match条件筛选
        // MatchOperation matchOperation = new MatchOperation(Criteria.where("newDepartment.departmentName").is("信息开发系统"));

        // 3、Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Employee.class, removeDollarOperation, lookupOperation);
        // TypedAggregation aggregation = Aggregation.newAggregation(Employee.class, removeDollarOperation, lookupOperation, matchOperation);
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, Document.class);

        String result = JSONArray.toJSONString(results.getMappedResults());
//        System.out.println(result);
        log.info(result);

        return result;
    }

    public String threeTableQuery() {
        // 1、消除@DBRef引用对象中的"$id"的"$"符号
        RemoveDollarOperation removeDollarOperation1 = new RemoveDollarOperation("newDepartmentFieldName", "department");

        // 2、使用mongodb $lookup实现左连接部门表
        LookupOperation lookupOperation1 = LookupOperation.newLookup().from("department")
                .localField("newDepartmentFieldName.id").foreignField("_id").as("newDepartment");

        // 3、使用$unwind展平步骤二中的左连接的department表的"newDepartment"
        UnwindOperation unwindOperation = new UnwindOperation(Fields.field("$newDepartment"));

        // 4、消除@DBRef引用对象中的"$id"的"$"符号
        RemoveDollarOperation removeDollarOperation2 = new RemoveDollarOperation("newCompanyFieldName", "newDepartment.company");

        // 5、使用mongodb $lookup实现左连接公司表
        LookupOperation lookupOperation2 = LookupOperation.newLookup().from("company")
                .localField("newCompanyFieldName.id").foreignField("_id").as("newCompany");

        MatchOperation matchOperation = new MatchOperation(Criteria.where("newCompany.companyName").is("XXX公司"));

        // 4、Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Employee.class,
                removeDollarOperation1, lookupOperation1,
                unwindOperation,
                removeDollarOperation2, lookupOperation2,
                matchOperation);

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, Document.class);

        String result =JSONArray.toJSONString(results.getMappedResults());
//        System.out.println(result);
        log.info(result);
        return result;
    }

    public String oneToManyTableQuery() {
        // 1、展平“多”的一方
        UnwindOperation unwindOperation = new UnwindOperation(Fields.field("employeeList"));

        // 2、消除@DBRef引用对象中的"$id"的"$"符号
        RemoveDollarOperation removeDollarOperation1 = new RemoveDollarOperation("newEmployeeFieldName", "employeeList");

        // 3、使用mongodb $lookup实现左连接员工表
        LookupOperation lookupOperation1 = LookupOperation.newLookup().from("employee")
                .localField("newEmployeeFieldName.id").foreignField("_id").as("newEmployee");

        // 筛选条件（非必须，看自己是否需要筛选）
        MatchOperation matchOperation = new MatchOperation(Criteria.where("newEmployee.employeeName").is("张一"));

        // 4、Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Employee.class,
                unwindOperation,
                removeDollarOperation1, lookupOperation1,
                matchOperation);

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, Document.class);
        String result =JSONArray.toJSONString(results.getMappedResults());
//        System.out.println(result);
        log.info(result);
        return result;
    }

}
