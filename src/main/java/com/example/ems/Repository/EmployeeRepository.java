package com.example.ems.Repository;

import com.example.ems.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmpCode(String empCode);

    Optional<Employee> findByEmail(String email);

    boolean existsByEmpCode(String empCode);

    List<Employee> findByDepartment(String department);
    List<Employee> findByDesignation(String designation);

}
