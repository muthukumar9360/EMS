package com.example.ems.Repository;

import com.example.ems.Model.Attendance;
import com.example.ems.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployee(Employee employee);

    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);

    List<Attendance> findByDateBetween(LocalDate start, LocalDate end);

}
