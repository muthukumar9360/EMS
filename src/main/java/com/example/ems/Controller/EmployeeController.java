package com.example.ems.Controller;

import com.example.ems.Model.Employee;
import com.example.ems.Model.User;
import com.example.ems.Security.CustomUserDetails;
import com.example.ems.Service.EmployeeService;
import com.example.ems.Service.LeaveRequestService;
import com.example.ems.Service.AttendanceService;
import com.example.ems.Service.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final LeaveRequestService leaveService;
    private final AttendanceService attendanceService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder; // FIXED

    public EmployeeController(EmployeeService employeeService,
                              LeaveRequestService leaveService,
                              AttendanceService attendanceService,
                              UserService userService,
                              PasswordEncoder passwordEncoder) {  // FIXED
        this.employeeService = employeeService;
        this.leaveService = leaveService;
        this.attendanceService = attendanceService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;  // FIXED
    }

    // ---------------- DASHBOARD ----------------
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails loggedIn, Model model) {

        Employee employee = loggedIn.getUser().getEmployeeProfile();

        model.addAttribute("employee", employee);
        model.addAttribute("attendance", attendanceService.getAttendanceByEmployee(employee));
        model.addAttribute("leaves", leaveService.getLeaveByEmployee(employee));

        return "employee/dashboard";
    }

    // ---------------- PROFILE ----------------
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails loggedIn, Model model) {
        model.addAttribute("employee", loggedIn.getUser().getEmployeeProfile());
        return "employee/profile";
    }

    // ---------------- CHANGE PASSWORD PAGE ----------------
    @GetMapping("/change-password")
    public String changePasswordPage() {
        return "employee/change-password";
    }

    // ---------------- CHANGE PASSWORD SUBMIT ----------------
    @PostMapping("/change-password")
    public String changePassword(
            @AuthenticationPrincipal CustomUserDetails loggedUser,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes
    ) {

        User user = loggedUser.getUser();

        // Check old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Old password is incorrect!");
            return "redirect:/employee/change-password";
        }

        // Check new password match
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "New passwords do not match!");
            return "redirect:/employee/change-password";
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(user);

        redirectAttributes.addFlashAttribute("success", "Password updated successfully!");
        return "redirect:/employee/dashboard";
    }

    @GetMapping("/edit-profile")
    public String editProfile(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model) {
        model.addAttribute("employee", loggedUser.getUser().getEmployeeProfile());
        return "employee/edit-profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(
            @AuthenticationPrincipal CustomUserDetails loggedUser,
            @RequestParam String empCode,
            @RequestParam String department,
            @RequestParam String designation,
            @RequestParam String phone,
            @RequestParam String email,
            RedirectAttributes redirectAttributes
    ) {

        Employee emp = loggedUser.getUser().getEmployeeProfile();

        emp.setEmpCode(empCode);
        emp.setDepartment(department);
        emp.setDesignation(designation);
        emp.setPhone(phone);
        emp.setEmail(email);

        employeeService.save(emp);

        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/employee/profile";
    }

}
