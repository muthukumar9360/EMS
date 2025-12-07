package com.example.ems.Service.Impl;

import com.example.ems.Model.Employee;
import com.example.ems.Model.User;
import com.example.ems.Repository.EmployeeRepository;
import com.example.ems.Service.EmployeeService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepo;

    public EmployeeServiceImpl(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public Employee createEmployee(Employee employee, User user) {
        employee.setUser(user); // sync both sides
        return employeeRepo.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee updated) {
        Employee employee = getEmployeeById(id);

        // Update editable fields
        employee.setDepartment(updated.getDepartment());
        employee.setDesignation(updated.getDesignation());
        employee.setEmail(updated.getEmail());
        employee.setPhone(updated.getPhone());

        // Optional update (if needed)
        if (updated.getEmpCode() != null) {
            employee.setEmpCode(updated.getEmpCode());
        }

        return employeeRepo.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }
}
