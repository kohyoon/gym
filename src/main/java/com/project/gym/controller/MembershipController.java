package com.project.gym.controller;

import com.project.gym.domain.Membership;
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
        membershipService.insertMembership(membership);
        return "redirect:/member/list"; //등록 후 목록으로
    }

    //===== 회원권 전체 목록 =====//
    @GetMapping("/list")
    public String getMembershipList(Model model) {
        List<Membership> membershipList = membershipService.getAllMemberships();
        model.addAttribute("membershipList", membershipList);
        return "membership/list";
    }

    //===== 회원권 상세 페이지 =====//
    @GetMapping("/detail/{id}")
    public String viewMembershipDetail(@PathVariable("id") Long membershipId, Model model) {
        Membership membership = membershipService.findByMembershipId(membershipId);
        System.out.println("************* membership: " + membership);
        model.addAttribute("membership", membership);
        return "membership/detail";
    }

    //===== 회원권 수정 폼 호출 =====//
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long membershipId, Model model) {
        Membership membership = membershipService.findByMembershipId(membershipId);
        model.addAttribute("membership",membership);
        return "membership/edit";
    }

    //===== 회원권 수정 처리 =====//
    @PostMapping("/edit")
    public String editMembership(@ModelAttribute Membership membership, Model model) {

        System.out.println("***************************** membership: " + membership);

        membershipService.updateMembership(membership);

        model.addAttribute("message", "회원권 정보가 수정되었습니다.");
        model.addAttribute("membership", membershipService.findByMembershipId(membership.getMembershipId()));

//        return "membership/detail";
        return "redirect:/membership/detail/" + membership.getMembershipId();
    }



}
