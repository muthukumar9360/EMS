package com.example.ems.Service;

import com.example.ems.Model.Employee;
import com.example.ems.Model.User;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    Employee createEmployee(Employee employee, User user);

    Employee updateEmployee(Long id, Employee employee);

    Employee getEmployeeById(Long id);

    List<Employee> getAllEmployees();

    void deleteEmployee(Long id);

    Employee save(Employee employee);

}
