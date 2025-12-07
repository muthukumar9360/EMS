package com.example.ems.Service;

import com.example.ems.Model.Payroll;
import com.example.ems.Model.Employee;

import java.util.List;

public interface PayrollService {

    Payroll generatePayroll(Payroll payroll);

    List<Payroll> getPayrollOfEmployee(Employee employee);
}
