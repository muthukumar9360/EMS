// com.example.ems.Service.DashboardService
package com.example.ems.Service;

import com.example.ems.Model.Employee;
import com.example.ems.Model.LeaveRequest;
import com.example.ems.Model.Attendance;
import com.example.ems.Repository.EmployeeRepository;
import com.example.ems.Repository.LeaveRequestRepository;
import com.example.ems.Repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final EmployeeRepository employeeRepo;
    private final LeaveRequestRepository leaveRepo;
    private final AttendanceRepository attendanceRepo;

    public DashboardService(EmployeeRepository employeeRepo,
                            LeaveRequestRepository leaveRepo,
                            AttendanceRepository attendanceRepo) {
        this.employeeRepo = employeeRepo;
        this.leaveRepo = leaveRepo;
        this.attendanceRepo = attendanceRepo;
    }

    public long totalEmployees() {
        return employeeRepo.count();
    }

    public long pendingLeavesCount() {
        return leaveRepo.countByStatus("PENDING");
    }

    public long totalAttendanceRecords() {
        return attendanceRepo.count();
    }

    public long todaysPresentCount() {
        LocalDate today = LocalDate.now();
        List<Attendance> list = attendanceRepo.findByDate(today);
        // assuming Attendance has 'status' or check-in presence; if not, count non-null checkin
        return list.stream().filter(a -> a.getCheckInTime() != null).count();
    }

    public List<Employee> recentEmployees() {
        return employeeRepo.findTop5ByOrderByIdDesc();
    }

    public List<LeaveRequest> recentPendingLeaves() {
        return leaveRepo.findByStatus("PENDING").stream().limit(10).collect(Collectors.toList());
    }

    // Department counts for pie chart
    public Map<String, Long> departmentCounts() {
        List<Employee> all = employeeRepo.findAll();
        return all.stream().collect(Collectors.groupingBy(
                e -> Optional.ofNullable(e.getDepartment()).orElse("Unknown"),
                Collectors.counting()
        ));
    }

    // Monthly attendance for last N days (date -> count)
    public Map<String, Long> attendanceCountsLastNDays(int days) {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days - 1);
        List<Attendance> attendances = attendanceRepo.findByDateBetween(start, end);
        Map<LocalDate, Long> map = attendances.stream()
                .filter(a -> a.getCheckInTime() != null) // present
                .collect(Collectors.groupingBy(Attendance::getDate, Collectors.counting()));

        // build result for each day (string date)
        Map<String, Long> result = new LinkedHashMap<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd");
        for (int i = 0; i < days; i++) {
            LocalDate d = start.plusDays(i);
            result.put(d.format(fmt), map.getOrDefault(d, 0L));
        }
        return result;
    }

    // Leave status distribution
    public Map<String, Long> leaveStatusDistribution() {
        List<LeaveRequest> all = leaveRepo.findAll();

        return all.stream().collect(Collectors.groupingBy(
                lr -> Optional.ofNullable(lr.getStatus())
                        .orElse(LeaveRequest.LeaveStatus.PENDING) // default enum
                        .name(),  // convert enum → String for chart labels
                Collectors.counting()
        ));
    }
}
