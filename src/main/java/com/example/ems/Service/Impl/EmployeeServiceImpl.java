package com.example.ems.Service.Impl;

import com.example.ems.Model.Employee;
import com.example.ems.Model.User;
import com.example.ems.Repository.EmployeeRepository;
import com.example.ems.Repository.PayrollRepository;
import com.example.ems.Repository.RoleRepository;
import com.example.ems.Repository.UserRepository;
import com.example.ems.Service.EmployeeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final UserRepository userRepo;
    private final PayrollRepository payrollRepository;
    private final RoleRepository userRoleRepo;

    public EmployeeServiceImpl(EmployeeRepository employeeRepo,
                               UserRepository userRepo,
                               PayrollRepository payrollRepository,
                               RoleRepository userRoleRepo) {
        this.employeeRepo = employeeRepo;
        this.userRepo = userRepo;
        this.payrollRepository = payrollRepository;
        this.userRoleRepo = userRoleRepo;
    }

    @Override
    public Employee createEmployee(Employee employee, User user) {
        employee.setUser(user);
        return employeeRepo.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee updated) {
        Employee employee = getEmployeeById(id);

        employee.setDepartment(updated.getDepartment());
        employee.setDesignation(updated.getDesignation());
        employee.setEmail(updated.getEmail());
        employee.setPhone(updated.getPhone());

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
    @Transactional
    public void deleteEmployee(Long id) {

        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        User user = emp.getUser();

        // 1️⃣ Delete all payrolls of this employee
        payrollRepository.deleteByEmployee(emp);

        // 2️⃣ Remove role mappings (clears users_roles table)
        if (user.getRoles() != null) {
            user.getRoles().clear();
            userRepo.save(user); // important to update mapping table
        }

        // 3️⃣ Delete employee record
        employeeRepo.delete(emp);

        // 4️⃣ Delete user login
        userRepo.delete(user);
    }


    @Override
    public Employee save(Employee employee) {
        return employeeRepo.save(employee);
    }
}
