package com.example.ems.Controller;

import com.example.ems.Model.Attendance;
import com.example.ems.Service.AttendanceService;
import com.example.ems.Service.EmployeeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/attendance")
public class AdminAttendanceController {

    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;

    public AdminAttendanceController(AttendanceService attendanceService,
                                     EmployeeService employeeService) {
        this.attendanceService = attendanceService;
        this.employeeService = employeeService;
    }

    // Attendance listing page (with optional filter)
    @GetMapping({"/report", ""})
    public String attendanceReport(
            @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Model model) {

        List<Attendance> attendance;

        if (start != null && end != null) {
            attendance = attendanceService.getAttendanceBetween(start, end);
        } else {
            attendance = attendanceService.getAllAttendance(); // provide this in service
        }

        model.addAttribute("attendance", attendance);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        return "admin/attendance-report"; // your attendance-report.html
    }
}
