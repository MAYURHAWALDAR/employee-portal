package com.mayur.devops.service;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.*;
class EmployeeServiceTest { @Test void returnsEmployees(){var list=new EmployeeService().findAll();assertEquals(3,list.size());assertEquals("DevOps Engineer",list.get(1).role());} }
