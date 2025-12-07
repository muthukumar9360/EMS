package com.example.ems.Service.Impl;

import com.example.ems.Model.CompanyInfo;
import com.example.ems.Repository.CompanyInfoRepository;
import com.example.ems.Service.CompanyInfoService;

import org.springframework.stereotype.Service;

@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    private final CompanyInfoRepository companyRepo;

    public CompanyInfoServiceImpl(CompanyInfoRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

    @Override
    public CompanyInfo getInfo() {
        return companyRepo.findById(1L)
                .orElseGet(() -> companyRepo.save(new CompanyInfo()));
    }

    @Override
    public CompanyInfo updateCompanyInfo(CompanyInfo info) {
        info.setId(1L);
        return companyRepo.save(info);
    }
}
