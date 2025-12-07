package com.example.ems.Config;

import com.example.ems.Model.Employee;
import com.example.ems.Model.Role;
import com.example.ems.Model.User;
import com.example.ems.Repository.EmployeeRepository;
import com.example.ems.Repository.RoleRepository;
import com.example.ems.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final EmployeeRepository employeeRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepo,
                           UserRepository userRepo,
                           EmployeeRepository employeeRepo,
                           PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // Ensure admin role exists
        Role adminRole = roleRepo.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepo.save(new Role("ROLE_ADMIN")));

        Role empRole = roleRepo.findByName("ROLE_EMPLOYEE")
                .orElseGet(() -> roleRepo.save(new Role("ROLE_EMPLOYEE")));

        createAdmin("admin1", "Administrator One", "ADM001",
                "Management", "System Admin", "admin1@ems.com", "9876543210", adminRole);

        createAdmin("admin2", "Administrator Two", "ADM002",
                "Operations", "Assistant Admin", "admin2@ems.com", "9123456789", adminRole);
    }

    private void createAdmin(String username, String fullName, String empCode,
                             String dept, String designation, String email,
                             String phone, Role adminRole) {

        if (userRepo.existsByUsername(username)) {
            return; // already exists
        }

        // Create User
        User user = new User();
        user.setUsername(username);
        user.setFullName(fullName);
        user.setPassword(passwordEncoder.encode("Admin@123"));
        user.setRoles(Set.of(adminRole));

        // Create Employee Profile
        Employee emp = new Employee();
        emp.setEmpCode(empCode);
        emp.setDepartment(dept);
        emp.setDesignation(designation);
        emp.setEmail(email);
        emp.setPhone(phone);

        // Bind both sides
        user.setEmployeeProfile(emp); // cascade saves employee

        // Save user -> cascade saves employee
        userRepo.save(user);

        System.out.println("ADMIN CREATED: " + username);
    }
}
