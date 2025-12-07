package com.example.ems.Service;

import com.example.ems.Model.LeaveRequest;
import com.example.ems.Model.Employee;

import java.util.List;

public interface LeaveRequestService {

    LeaveRequest applyLeave(LeaveRequest request);

    List<LeaveRequest> getLeaveByEmployee(Employee employee);

    List<LeaveRequest> getPendingRequests();

    LeaveRequest approveLeave(Long id);

    LeaveRequest rejectLeave(Long id);
}
