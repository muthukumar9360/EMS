package com.example.ems.Config;

import com.example.ems.Model.*;
import com.example.ems.Repository.*;

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

        // Create roles if missing
        Role adminRole = roleRepo.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepo.save(new Role("ROLE_ADMIN")));

        Role empRole = roleRepo.findByName("ROLE_EMPLOYEE")
                .orElseGet(() -> roleRepo.save(new Role("ROLE_EMPLOYEE")));

        // Create admin user if missing
        if (!userRepo.existsByUsername("admin")) {

            User admin = new User();
            admin.setFullName("Administrator");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRoles(Set.of(adminRole));
            userRepo.save(admin);

            // Create admin employee profile
            Employee adminEmp = new Employee();
            adminEmp.setEmpCode("ADMIN001");
            adminEmp.setDepartment("Management");
            adminEmp.setDesignation("System Administrator");
            adminEmp.setEmail("admin@ems.com");
            adminEmp.setPhone("9999999999");
            adminEmp.setUser(admin);

            employeeRepo.save(adminEmp);

            System.out.println("DEFAULT ADMIN USER CREATED: admin / Admin@123");
        }
    }
}
