package com.example.ems.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String empCode;
    private String department;
    private String designation;
    private String email;
    private String phone;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    private List<Payroll> payrolls;

    public Employee() {}

    public Employee(Long id, String empCode, String department,
                    String designation, String email, String phone, User user) {
        this.id = id;
        this.empCode = empCode;
        this.department = department;
        this.designation = designation;
        this.email = email;
        this.phone = phone;
        this.user = user;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmpCode() { return empCode; }
    public void setEmpCode(String empCode) { this.empCode = empCode; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public User getUser() { return user; }
    public void setUser(User user) {
        this.user = user;
        if (user != null && user.getEmployeeProfile() != this) {
            user.setEmployeeProfile(this);
        }
    }
}
