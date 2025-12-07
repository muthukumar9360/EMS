package com.example.ems.Service.Impl;

import com.example.ems.Model.Payroll;
import com.example.ems.Model.Employee;
import com.example.ems.Repository.PayrollRepository;
import com.example.ems.Service.PayrollService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepo;

    public PayrollServiceImpl(PayrollRepository payrollRepo) {
        this.payrollRepo = payrollRepo;
    }

    @Override
    public Payroll generatePayroll(Payroll payroll) {

        if (payroll.getEmployee() == null) {
            throw new RuntimeException("Payroll must be assigned to an employee.");
        }

        return payrollRepo.save(payroll);
    }

    @Override
    public List<Payroll> getPayrollOfEmployee(Employee employee) {
        return payrollRepo.findByEmployee(employee);
    }
}
