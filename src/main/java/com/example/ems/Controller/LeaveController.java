package com.example.ems.Controller;

import com.example.ems.Model.Employee;
import com.example.ems.Model.LeaveRequest;
import com.example.ems.Security.CustomUserDetails;
import com.example.ems.Service.LeaveRequestService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveRequestService leaveService;

    public LeaveController(LeaveRequestService leaveService) {
        this.leaveService = leaveService;
    }

    // ---------------- EMPLOYEE FEATURES ----------------

    @GetMapping("/apply")
    public String applyPage() {
        return "employee/apply-leave";
    }

    @PostMapping("/apply")
    public String applyLeave(@AuthenticationPrincipal CustomUserDetails loggedIn,
                             @ModelAttribute LeaveRequest request,
                             RedirectAttributes redirectAttributes) {

        Employee employee = loggedIn.getUser().getEmployeeProfile();
        request.setEmployee(employee);

        leaveService.applyLeave(request);
        redirectAttributes.addFlashAttribute("success", "Leave applied successfully!");

        return "redirect:/employee/dashboard";
    }

    @GetMapping("/my-requests")
    public String myRequests(@AuthenticationPrincipal CustomUserDetails loggedIn,
                             Model model) {

        Employee emp = loggedIn.getUser().getEmployeeProfile();
        model.addAttribute("requests", leaveService.getLeaveByEmployee(emp));

        return "employee/my-leave-requests";
    }

    // ---------------- ADMIN FEATURES ----------------

    @GetMapping("/pending")
    public String pendingRequests(Model model) {

        model.addAttribute("pendingRequests", leaveService.getPendingRequests());

        return "admin/pending-leaves";
    }

    @GetMapping("/approve/{id}")
    public String approve(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        leaveService.approveLeave(id);
        redirectAttributes.addFlashAttribute("success", "Leave Approved!");

        return "redirect:/leave/pending";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        leaveService.rejectLeave(id);
        redirectAttributes.addFlashAttribute("error", "Leave Rejected!");

        return "redirect:/leave/pending";
    }
}
