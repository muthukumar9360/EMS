package com.example.ems.Controller;

import com.example.ems.Model.CompanyInfo;
import com.example.ems.Service.CompanyInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/company")
public class AdminCompanyController {

    private final CompanyInfoService companyInfoService;

    public AdminCompanyController(CompanyInfoService companyInfoService) {
        this.companyInfoService = companyInfoService;
    }

    @GetMapping
    public String companyInfoPage(Model model) {
        CompanyInfo info = companyInfoService.getInfo();

        if (info == null) {
            info = new CompanyInfo(); // PREVENT NULL
        }

        model.addAttribute("info", info);
        return "admin/company-info";
    }

    @PostMapping
    public String updateCompanyInfo(@ModelAttribute CompanyInfo info,
                                    RedirectAttributes redirectAttributes) {

        companyInfoService.updateCompanyInfo(info);
        redirectAttributes.addFlashAttribute("success", "Company information updated.");

        return "redirect:/admin/company";  // <-- FIX
    }
}

