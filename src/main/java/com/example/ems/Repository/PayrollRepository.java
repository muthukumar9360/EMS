package com.example.ems.Repository;

import com.example.ems.Model.Employee;
import com.example.ems.Model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByEmployee(Employee employee);

    List<Payroll> findByPayDateBetween(LocalDate start, LocalDate end);
}
