package com.example.ems.util;

import com.example.ems.Model.*;
import com.example.ems.dto.*;

public class MapperUtil {

    // ---------------------------------------------------
    // USER + EMPLOYEE MAPPING
    // ---------------------------------------------------
    public static EmployeeDto toEmployeeDto(Employee employee) {
        if (employee == null) return null;

        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setEmpCode(employee.getEmpCode());
        dto.setDepartment(employee.getDepartment());
        dto.setDesignation(employee.getDesignation());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());

        if (employee.getUser() != null) {
            dto.setFullName(employee.getUser().getFullName());
            dto.setUsername(employee.getUser().getUsername());
        }

        return dto;
    }

    public static Employee toEmployee(EmployeeDto dto) {
        if (dto == null) return null;

        Employee emp = new Employee();
        emp.setId(dto.getId());
        emp.setEmpCode(dto.getEmpCode());
        emp.setDepartment(dto.getDepartment());
        emp.setDesignation(dto.getDesignation());
        emp.setEmail(dto.getEmail());
        emp.setPhone(dto.getPhone());

        return emp;
    }

    public static User toUserFromRegisterDto(RegisterDto dto, String encodedPassword) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setUsername(dto.getUsername());
        user.setPassword(encodedPassword);
        return user;
    }

    // ---------------------------------------------------
    // ATTENDANCE MAPPING
    // ---------------------------------------------------
    public static AttendanceDto toAttendanceDto(Attendance att) {
        if (att == null) return null;

        AttendanceDto dto = new AttendanceDto();
        dto.setId(att.getId());
        dto.setDate(att.getDate());
        dto.setCheckInTime(att.getCheckInTime());
        dto.setCheckOutTime(att.getCheckOutTime());
        dto.setStatus(att.getStatus());

        if (att.getEmployee() != null) {
            dto.setEmployeeId(att.getEmployee().getId());
            dto.setEmployeeName(att.getEmployee().getUser() != null
                    ? att.getEmployee().getUser().getFullName()
                    : null);
        }

        return dto;
    }

    // ---------------------------------------------------
    // COMPANY INFO
    // ---------------------------------------------------
    public static CompanyInfoDto toCompanyInfoDto(CompanyInfo c) {
        if (c == null) return null;

        CompanyInfoDto dto = new CompanyInfoDto();
        dto.setName(c.getName());
        dto.setAddress(c.getAddress());
        dto.setContactEmail(c.getContactEmail());
        dto.setContactPhone(c.getContactPhone());

        return dto;
    }

    public static CompanyInfo toCompanyInfo(CompanyInfoDto dto) {
        if (dto == null) return null;

        CompanyInfo c = new CompanyInfo();
        c.setId(1L);
        c.setName(dto.getName());
        c.setAddress(dto.getAddress());
        c.setContactEmail(dto.getContactEmail());
        c.setContactPhone(dto.getContactPhone());

        return c;
    }

    // ---------------------------------------------------
    // LEAVE REQUEST
    // ---------------------------------------------------
    public static LeaveRequestDto toLeaveDto(LeaveRequest lr) {
        if (lr == null) return null;

        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId(lr.getId());
        dto.setStartDate(lr.getStartDate());
        dto.setEndDate(lr.getEndDate());
        dto.setLeaveType(lr.getLeaveType());
        dto.setReason(lr.getReason());
        dto.setStatus(lr.getStatus().toString());

        if (lr.getEmployee() != null) {
            dto.setEmployeeId(lr.getEmployee().getId());
            dto.setEmployeeName(
                    lr.getEmployee().getUser() != null ?
                            lr.getEmployee().getUser().getFullName() :
                            null
            );
        }

        return dto;
    }

    public static LeaveRequest toLeaveEntity(LeaveRequestDto dto, Employee employee) {
        LeaveRequest lr = new LeaveRequest();

        lr.setStartDate(dto.getStartDate());
        lr.setEndDate(dto.getEndDate());
        lr.setLeaveType(dto.getLeaveType());
        lr.setReason(dto.getReason());
        lr.setEmployee(employee);
        lr.setStatus(LeaveRequest.LeaveStatus.PENDING);

        return lr;
    }

    // ---------------------------------------------------
    // PAYROLL
    // ---------------------------------------------------
    public static PayrollDto toPayrollDto(Payroll p) {
        if (p == null) return null;

        PayrollDto dto = new PayrollDto();
        dto.setId(p.getId());
        dto.setBasic(p.getBasic());
        dto.setAllowances(p.getAllowances());
        dto.setDeductions(p.getDeductions());
        dto.setNetPay(p.getNetPay());
        dto.setPayDate(p.getPayDate());

        if (p.getEmployee() != null) {
            dto.setEmployeeId(p.getEmployee().getId());
            dto.setEmployeeName(
                    p.getEmployee().getUser() != null ?
                            p.getEmployee().getUser().getFullName() :
                            null
            );
        }

        return dto;
    }

}
