package com.project.gym.controller;

import com.project.gym.domain.Member;
import com.project.gym.domain.Membership;
import com.project.gym.service.MemberService;
import com.project.gym.service.MembershipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/membership")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    //===== 회원권 등록 폼 호출 =====//
    @GetMapping("/register")
    public String showMembershipRegisterForm(Model model) {
        //Membership membership = new Membership();
        model.addAttribute("membership", new Membership());
        return "membership/register";
    }

    //===== 회원권 등록 처리 =====//
    @PostMapping("/register")
    public String registerMembership(@ModelAttribute Membership membership) {
        // END_DATE 자동 계산
        membership.setEndDate(
                membership.getStartDate().plusDays(membership.getPeriodDays() - 1)
        );
        membershipService.insertMembership(membership);
        return "redirect:/membership/list"; //등록 후 목록으로
    }



}
