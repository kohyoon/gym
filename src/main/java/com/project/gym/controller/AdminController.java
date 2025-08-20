package com.project.gym.controller;

import com.project.gym.common.PageResult;
import com.project.gym.domain.Admin;
import com.project.gym.dto.admin.AdminListDTO;
import com.project.gym.dto.admin.AdminSearchCriteria;
import com.project.gym.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        if(adminService.isUserIdDuplicate(admin.getAdminLoginId())) {
            model.addAttribute("error", "이미 사용중인 아이디입니다.");
            return "admin/signup";
        }

        // 비밀번호 확인
        if(!admin.getAdminPassword().equals(admin.getConfirmPassword())) {
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

    //===== 회원 목록 조회 =====//
    @GetMapping("/search")
    public String showAdminSearchLists(@ModelAttribute("cond") AdminSearchCriteria criteria, Model model) {
        PageResult<AdminListDTO> page = adminService.getAdminByCriteria(criteria);
        model.addAttribute("page", page);
        model.addAttribute("adminList", page.content());
        model.addAttribute("cond", criteria);

        return "admin/list";
    }


    // 회원 정보 수정 폼 호출
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long adminId, Model model) {
        Admin admin = adminService.getAdminById(adminId);

        model.addAttribute("admin", admin);

        return "admin/edit";
    }

    // 정보 수정 처리
    @PostMapping("/edit")
    public String processAdminEdit(Admin admin) {
        System.out.println("****** admin: " + admin);

        adminService.updateAdmin(admin);

        return "redirect:/admin/adminPage";
    }

    // 관리자 퇴사 처리
    @PostMapping("/resign/{id}")
    public String resignAdmin(@PathVariable("id") Long adminId) {

        adminService.resignAdmin(adminId);

        return "redirect:/admin/list";
    }




}
