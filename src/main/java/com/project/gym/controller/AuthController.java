package com.project.gym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/admin_login")
    public String showAdminLoginForm() {
        return "auth/admin_login";
    }

    @GetMapping("/logout-success")
    public String logoutPage() {
        return "logout"; //
    }

    @GetMapping("/member_login")
    public String showMemberLoginForm() { return "auth/member_login"; }


}
