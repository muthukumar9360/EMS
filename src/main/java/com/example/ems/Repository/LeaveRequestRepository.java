package com.example.ems.Repository;

import com.example.ems.Model.Employee;
import com.example.ems.Model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployee(Employee employee);

    List<LeaveRequest> findByStatus(LeaveRequest.LeaveStatus status);

    List<LeaveRequest> findByStartDateBetween(LocalDate start, LocalDate end);

    long countByStatus(String status);
    List<LeaveRequest> findByStatus(String status);
    List<LeaveRequest> findTop5ByOrderByIdDesc();

}
