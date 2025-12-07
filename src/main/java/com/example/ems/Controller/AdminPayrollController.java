package com.example.ems.Controller;

import com.example.ems.Model.Employee;
import com.example.ems.Model.Payroll;
import com.example.ems.Service.EmployeeService;
import com.example.ems.Service.PayrollService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/payroll")
public class AdminPayrollController {

    private final EmployeeService employeeService;
    private final PayrollService payrollService;

    public AdminPayrollController(EmployeeService employeeService, PayrollService payrollService) {
        this.employeeService = employeeService;
        this.payrollService = payrollService;
    }

    // Show generation form
    @GetMapping("/generate")
    public String showPayrollForm(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "admin/generate-payroll";
    }

    // Handle payroll generation
    @PostMapping("/generate")
    public String generatePayroll(
            @RequestParam Long employeeId,
            @RequestParam double basic,
            @RequestParam(required = false, defaultValue = "0") double allowances,
            @RequestParam(required = false, defaultValue = "0") double deductions,
            @RequestParam("payDate") String payDateStr,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Employee emp = employeeService.getEmployeeById(employeeId);
            LocalDate payDate = LocalDate.parse(payDateStr);

            payrollService.generatePayrollForEmployee(emp, basic, allowances, deductions, payDate);

            redirectAttributes.addFlashAttribute("success", "Payroll generated for " + emp.getUser().getFullName());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Failed to generate payroll: " + ex.getMessage());
        }
        return "admin/generate-payroll";
    }

    @GetMapping("/list")
    public String viewAllPayrolls(Model model) {
        List<Payroll> payrolls = payrollService.getAllPayrolls();
        model.addAttribute("payrolls", payrolls);
        return "admin/payrolls-list";
    }

}
