package com.example.ems.Service;

import com.example.ems.Model.CompanyInfo;

public interface CompanyInfoService {

    CompanyInfo getInfo();

    CompanyInfo updateCompanyInfo(CompanyInfo info);
}
