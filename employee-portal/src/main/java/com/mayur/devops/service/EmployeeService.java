package com.mayur.devops.service;
import com.mayur.devops.model.Employee; import java.util.List;
public class EmployeeService { public List<Employee> findAll(){ return List.of(new Employee(101,"Asha","Cloud Engineer"),new Employee(102,"Rahul","DevOps Engineer"),new Employee(103,"Meera","SRE")); } }
