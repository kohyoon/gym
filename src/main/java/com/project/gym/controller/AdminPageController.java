package com.project.gym.controller;

import com.project.gym.domain.Admin;
import com.project.gym.domain.AdminDetails;
import com.project.gym.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminPageController {

    private final AdminService adminService;

    @GetMapping("/admin/adminPage")
    public String adminPage(@AuthenticationPrincipal AdminDetails adminDetails, Model model) {
        Admin admin = adminService.getAdminById(adminDetails.getAdmin().getAdminId());

        model.addAttribute("admin", admin);

        return "admin/adminPage";
    }


}
