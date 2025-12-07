package com.example.ems.Controller;

import com.example.ems.Model.Employee;
import com.example.ems.Model.User;
import com.example.ems.Service.EmployeeService;
import com.example.ems.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/employees")
public class AdminEmployeeController {

    private final EmployeeService employeeService;
    private final UserService userService;

    public AdminEmployeeController(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }

    // List all employees page
    @GetMapping
    public String listEmployees(Model model, @RequestParam(value = "success", required = false) String success) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        if (success != null) model.addAttribute("success", success);
        return "admin/employees"; // your employees.html template
    }

    // Show add employee page
    @GetMapping("/add")
    public String addEmployeePage() {
        return "admin/add-employee"; // your add-employee.html
    }

    // Handle add employee form
    @PostMapping("/add")
    public String addEmployee(
            @RequestParam String fullName,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String empCode,
            @RequestParam String department,
            @RequestParam String designation,
            @RequestParam String email,
            @RequestParam String phone,
            RedirectAttributes redirectAttributes
    ) {
        try {
            // create user + employee (userService.registerUser creates user and an empty Employee linked)
            User user = userService.registerUser(fullName, username, password);

            // get employee profile created by registerUser and fill fields
            Employee emp = user.getEmployeeProfile();
            if (emp == null) {
                // fallback: create new employee and link
                emp = new Employee();
                emp.setUser(user);
            }
            emp.setEmpCode(empCode);
            emp.setDepartment(department);
            emp.setDesignation(designation);
            emp.setEmail(email);
            emp.setPhone(phone);

            employeeService.save(emp);

            redirectAttributes.addFlashAttribute("success", "Employee added successfully!");
            return "redirect:/admin/employees?success=Employee+added";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Failed to add employee: " + ex.getMessage());
            return "redirect:/admin/employees/add";
        }
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String editEmployeePage(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            model.addAttribute("employee", employee);
            return "admin/edit-employee"; // your edit-employee.html
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Employee not found");
            return "redirect:/admin/employees";
        }
    }

    // Submit edit
    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @RequestParam String empCode,
                                 @RequestParam String department,
                                 @RequestParam String designation,
                                 @RequestParam String email,
                                 @RequestParam String phone,
                                 RedirectAttributes redirectAttributes) {

        try {
            Employee existing = employeeService.getEmployeeById(id);
            existing.setEmpCode(empCode);
            existing.setDepartment(department);
            existing.setDesignation(designation);
            existing.setEmail(email);
            existing.setPhone(phone);

            employeeService.save(existing);

            redirectAttributes.addFlashAttribute("success", "Employee updated successfully!");
            return "redirect:/admin/employees";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Failed to update: " + ex.getMessage());
            return "redirect:/admin/employees";
        }
    }

    // Delete employee
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteEmployee(id);
            System.out.println("Employee Deleted Successfully....");
            redirectAttributes.addFlashAttribute("success", "Employee deleted.");
        } catch (Exception ex) {
            System.out.println("Employee Not Deleted...");
            redirectAttributes.addFlashAttribute("error", "Failed to delete: " + ex.getMessage());
        }
        return "redirect:/admin/employees";
    }
}
