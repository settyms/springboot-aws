package com.aws.demo.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int eno;
    private String ename;
    private int salary;
    private String designation;
    private Department department;
}
