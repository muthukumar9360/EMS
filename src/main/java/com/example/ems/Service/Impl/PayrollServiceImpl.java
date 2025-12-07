package com.example.ems.Service.Impl;

import com.example.ems.Model.Payroll;
import com.example.ems.Model.Employee;
import com.example.ems.Repository.PayrollRepository;
import com.example.ems.Service.PayrollService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepo;

    public PayrollServiceImpl(PayrollRepository payrollRepo) {
        this.payrollRepo = payrollRepo;
    }

    @Override
    public List<Payroll> getAllPayrolls() {
        return payrollRepo.findAll();
    }


    @Override
    public Payroll generatePayroll(Payroll payroll) {

        if (payroll.getEmployee() == null) {
            throw new RuntimeException("Payroll must be assigned to an employee.");
        }

        // No net salary stored → net pay is calculated dynamically
        return payrollRepo.save(payroll);
    }

    @Override
    public List<Payroll> getPayrollOfEmployee(Employee employee) {
        return payrollRepo.findByEmployee(employee);
    }

    @Override
    public void generatePayrollForEmployee(Employee emp, double basic, double allowances, double deductions, LocalDate payDate) {

        Payroll payroll = new Payroll();
        payroll.setEmployee(emp);
        payroll.setBasic(basic);
        payroll.setAllowances(allowances);
        payroll.setDeductions(deductions);
        payroll.setPayDate(payDate);

        payrollRepo.save(payroll);
    }
}
