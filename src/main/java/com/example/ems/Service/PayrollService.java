package com.example.ems.Service;

import com.example.ems.Model.Payroll;
import com.example.ems.Model.Employee;

import java.time.LocalDate;
import java.util.List;

public interface PayrollService {

    Payroll generatePayroll(Payroll payroll);

    List<Payroll> getPayrollOfEmployee(Employee employee);

    List<Payroll> getAllPayrolls();

    void generatePayrollForEmployee(Employee emp, double basic, double allowances, double deductions, LocalDate payDate);
}
