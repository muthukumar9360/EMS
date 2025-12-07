package com.example.ems.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payrolls")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private double basic;
    private double allowances;
    private double deductions;
    private LocalDate payDate;

    public Payroll() {}

    public Payroll(Long id, Employee employee, double basic,
                   double allowances, double deductions, LocalDate payDate) {
        this.id = id;
        this.employee = employee;
        this.basic = basic;
        this.allowances = allowances;
        this.deductions = deductions;
        this.payDate = payDate;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public double getBasic() { return basic; }
    public void setBasic(double basic) { this.basic = basic; }

    public double getAllowances() { return allowances; }
    public void setAllowances(double allowances) { this.allowances = allowances; }

    public double getDeductions() { return deductions; }
    public void setDeductions(double deductions) { this.deductions = deductions; }

    public LocalDate getPayDate() { return payDate; }
    public void setPayDate(LocalDate payDate) { this.payDate = payDate; }

    public double getNetPay() {
        return basic + allowances - deductions;
    }
}
