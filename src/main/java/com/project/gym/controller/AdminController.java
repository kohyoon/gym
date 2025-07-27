package com.project.gym.controller;

import com.project.gym.domain.Admin;
import com.project.gym.domain.AdminDetails;
import com.project.gym.service.AdminService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;;

    public AdminController (AdminService adminService) {
        this.adminService = adminService;
    }


    // 관리자 회원가입 폼 호출
    @GetMapping("/signup")
    public String showAdminSignUpForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/signup";
    }

    // 관리자 회원가입 처리
    @PostMapping("/signup")
    public String processAdminSignUp(@ModelAttribute("admin") Admin admin, Model model) {

        // 아이디 중복여부 확인
        if(adminService.isUserIdDuplicate(admin.getUserId())) {
            model.addAttribute("error", "이미 사용중인 아이디입니다.");
            return "admin/signup";
        }

        // 비밀번호 확인
        if(!admin.getPassword().equals(admin.getConfirmPassword())) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "admin/signup";
        }

        adminService.saveAdmin(admin);
        return "redirect:/auth/login";
    }

    // 아이디 중복 확인
    @GetMapping("/check-userid")
    @ResponseBody
    public boolean checkUserId(@RequestParam String userId) {
        return !adminService.isUserIdDuplicate(userId);
    }


    // 관리자 목록 출력
    @GetMapping("/list")
    public String showAdminList(Model model, @AuthenticationPrincipal AdminDetails adminDetails) {
        List<Admin> adminList = adminService.getAllAdmins();
        model.addAttribute("adminList", adminList);
        model.addAttribute("adminName", adminDetails.getAdmin().getAdminName());
        return "admin/list";
    }





}
