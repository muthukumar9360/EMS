package com.example.ems.Service.Impl;

import com.example.ems.Model.Employee;
import com.example.ems.Model.Role;
import com.example.ems.Model.User;
import com.example.ems.Repository.EmployeeRepository;
import com.example.ems.Repository.RoleRepository;
import com.example.ems.Repository.UserRepository;
import com.example.ems.Service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final EmployeeRepository employeeRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo,
                           RoleRepository roleRepo,
                           EmployeeRepository employeeRepo,
                           PasswordEncoder passwordEncoder) {

        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // FIXED ✔
    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public User registerUser(String fullName, String username, String rawPassword) {

        if (userRepo.existsByUsername(username)) {
            throw new RuntimeException("Username already exists.");
        }

        // Create User
        User user = new User();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));

        Role empRole = roleRepo.findByName("ROLE_EMPLOYEE")
                .orElseThrow(() -> new RuntimeException("Employee role missing."));

        user.setRoles(Set.of(empRole));

        User savedUser = userRepo.save(user);

        // Create empty employee profile
        Employee emp = new Employee();
        emp.setUser(savedUser);

        employeeRepo.save(emp);

        return savedUser;
    }

    @Override
    public User createUserWithRoles(User user, List<Role> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.copyOf(roles));
        return userRepo.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }
}
