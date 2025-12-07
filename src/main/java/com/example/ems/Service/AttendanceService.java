package com.example.ems.Service;

import com.example.ems.Model.Attendance;
import com.example.ems.Model.Employee;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    Attendance markAttendance(Employee employee);

    Attendance markCheckout(Employee employee);

    List<Attendance> getAttendanceByEmployee(Employee employee);

    List<Attendance> getAttendanceByDate(LocalDate date);

    List<Attendance> getAttendanceBetween(LocalDate start,LocalDate end);

    List<Attendance> getAllAttendance();
}
