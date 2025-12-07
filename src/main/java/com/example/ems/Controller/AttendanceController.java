package com.example.ems.Controller;

import com.example.ems.Model.Employee;
import com.example.ems.Security.CustomUserDetails;
import com.example.ems.Service.AttendanceService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check-in")
    public String checkIn(@AuthenticationPrincipal CustomUserDetails loggedIn,
                          RedirectAttributes redirectAttributes) {

        Employee employee = loggedIn.getUser().getEmployeeProfile();

        try {
            attendanceService.markAttendance(employee);
            redirectAttributes.addFlashAttribute("success", "Checked-in successfully!");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/employee/dashboard";
    }

    @PostMapping("/check-out")
    public String checkOut(@AuthenticationPrincipal CustomUserDetails loggedIn,
                           RedirectAttributes redirectAttributes) {

        Employee employee = loggedIn.getUser().getEmployeeProfile();

        try {
            attendanceService.markCheckout(employee);
            redirectAttributes.addFlashAttribute("success", "Checked-out successfully!");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/employee/dashboard";
    }
}
