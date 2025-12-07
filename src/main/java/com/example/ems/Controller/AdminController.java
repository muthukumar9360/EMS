package com.example.ems.Controller;

import com.example.ems.Service.EmployeeService;
import com.example.ems.Service.LeaveRequestService;
import com.example.ems.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EmployeeService employeeService;
    private final LeaveRequestService leaveRequestService;

    public AdminController(EmployeeService employeeService,
                           LeaveRequestService leaveRequestService) {
        this.employeeService = employeeService;
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(@AuthenticationPrincipal CustomUserDetails loggedInUser,
                                 Model model) {

        model.addAttribute("admin", loggedInUser.getUser());
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("pendingLeaves", leaveRequestService.getPendingRequests());

        return "admin/dashboard";
    }
}
