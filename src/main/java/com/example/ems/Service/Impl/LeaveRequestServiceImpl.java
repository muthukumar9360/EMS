package com.example.ems.Service.Impl;

import com.example.ems.Model.Employee;
import com.example.ems.Model.LeaveRequest;
import com.example.ems.Repository.LeaveRequestRepository;
import com.example.ems.Service.LeaveRequestService;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRepo;

    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRepo) {
        this.leaveRepo = leaveRepo;
    }

    @Override
    public LeaveRequest applyLeave(LeaveRequest request) {

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new RuntimeException("Start date cannot be after end date.");
        }

        if (request.getStartDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot apply leave for past dates.");
        }

        // Prevent overlapping leaves
        List<LeaveRequest> existing = leaveRepo.findByEmployee(request.getEmployee());
        for (LeaveRequest lr : existing) {
            boolean overlap =
                    !(request.getEndDate().isBefore(lr.getStartDate()) ||
                            request.getStartDate().isAfter(lr.getEndDate()));

            if (overlap) {
                throw new RuntimeException("Leave dates overlap with an existing request.");
            }
        }

        request.setStatus(LeaveRequest.LeaveStatus.PENDING);
        return leaveRepo.save(request);
    }

    @Override
    public List<LeaveRequest> getLeaveByEmployee(Employee employee) {
        return leaveRepo.findByEmployee(employee);
    }

    @Override
    public List<LeaveRequest> getPendingRequests() {
        return leaveRepo.findByStatus(LeaveRequest.LeaveStatus.PENDING);
    }

    @Override
    public LeaveRequest approveLeave(Long id) {
        LeaveRequest lr = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        lr.setStatus(LeaveRequest.LeaveStatus.APPROVED);
        return leaveRepo.save(lr);
    }

    @Override
    public LeaveRequest rejectLeave(Long id) {
        LeaveRequest lr = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        lr.setStatus(LeaveRequest.LeaveStatus.REJECTED);
        return leaveRepo.save(lr);
    }
}
