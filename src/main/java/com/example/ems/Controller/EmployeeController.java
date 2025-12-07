package com.example.ems.Controller;

import com.example.ems.Model.Employee;
import com.example.ems.Security.CustomUserDetails;
import com.example.ems.Service.EmployeeService;
import com.example.ems.Service.LeaveRequestService;
import com.example.ems.Service.AttendanceService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final LeaveRequestService leaveService;
    private final AttendanceService attendanceService;

    public EmployeeController(EmployeeService employeeService,
                              LeaveRequestService leaveService,
                              AttendanceService attendanceService) {
        this.employeeService = employeeService;
        this.leaveService = leaveService;
        this.attendanceService = attendanceService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails loggedIn, Model model) {

        Employee employee = loggedIn.getUser().getEmployeeProfile();

        model.addAttribute("employee", employee);
        model.addAttribute("attendance", attendanceService.getAttendanceByEmployee(employee));
        model.addAttribute("leaves", leaveService.getLeaveByEmployee(employee));

        return "employee/dashboard";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails loggedIn, Model model) {

        model.addAttribute("employee", loggedIn.getUser().getEmployeeProfile());
        return "employee/profile";
    }
}
