package com.example.ems.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "company_info")
public class CompanyInfo {

    @Id
    private Long id = 1L;

    private String name;
    private String address;
    private String contactEmail;
    private String contactPhone;

    public CompanyInfo() {}

    public CompanyInfo(Long id, String name, String address,
                       String contactEmail, String contactPhone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
}
