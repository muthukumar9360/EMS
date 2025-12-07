package com.example.ems.Controller;

import com.example.ems.Model.Employee;
import com.example.ems.Security.CustomUserDetails;
import com.example.ems.Service.EmployeeService;
import com.example.ems.Service.PayrollService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payroll")
public class PayrollController {

    private final PayrollService payrollService;
    private final EmployeeService employeeService;

    public PayrollController(PayrollService payrollService,
                             EmployeeService employeeService) {
        this.payrollService = payrollService;
        this.employeeService = employeeService;
    }

    @GetMapping("/history")
    public String payrollHistory(@AuthenticationPrincipal CustomUserDetails loggedIn,
                                 Model model) {

        Employee employee = loggedIn.getUser().getEmployeeProfile();

        model.addAttribute("employee", employee);
        model.addAttribute("payrolls", payrollService.getPayrollOfEmployee(employee));

        return "employee/payroll-history";
    }
}
