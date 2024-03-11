package com.aws.demo.services;

import com.aws.demo.models.Employee;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class S3Service {

    @Autowired
    private S3Template s3Template;

    @Value("${aws.s3.bucket}")
    private String s3Bucket;

    public String uploadFile(Employee employee)
    {
        String folderPath = String.format("employees/%d.json",employee.getEno());
        s3Template.store(s3Bucket,folderPath,employee);
        return "file has been uploaded";
    }
    public Employee downFile(Integer employeeNo)
    {
        String folderPath = String.format("employees/%d.json",employeeNo);
        Employee e = s3Template.read(s3Bucket,folderPath,Employee.class);
        return e;
    }

}
