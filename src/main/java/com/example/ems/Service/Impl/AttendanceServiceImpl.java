package com.example.ems.Service.Impl;

import com.example.ems.Model.Attendance;
import com.example.ems.Model.Employee;
import com.example.ems.Repository.AttendanceRepository;
import com.example.ems.Service.AttendanceService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepo;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepo) {
        this.attendanceRepo = attendanceRepo;
    }

    @Override
    public Attendance markAttendance(Employee employee) {
        LocalDate today = LocalDate.now();

        // ❗ Prevent Duplicate Check-in
        List<Attendance> todayRecords = attendanceRepo.findByEmployeeAndDate(employee, today);
        if (!todayRecords.isEmpty()) {
            throw new RuntimeException("You have already checked in today.");
        }

        Attendance att = new Attendance(
                null,
                employee,
                today,
                LocalDateTime.now(),
                null,
                "Present"
        );

        return attendanceRepo.save(att);
    }

    @Override
    public Attendance markCheckout(Employee employee) {
        Attendance att = attendanceRepo.findByEmployeeAndDate(employee, LocalDate.now())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Check-in record not found for today."));

        if (att.getCheckOutTime() != null) {
            throw new RuntimeException("You have already checked out today.");
        }

        att.setCheckOutTime(LocalDateTime.now());
        return attendanceRepo.save(att);
    }

    @Override
    public List<Attendance> getAttendanceByEmployee(Employee employee) {
        return attendanceRepo.findByEmployee(employee);
    }

    @Override
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepo.findByDate(date);
    }
}
