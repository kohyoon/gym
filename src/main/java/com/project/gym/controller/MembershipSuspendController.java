package com.project.gym.controller;

import com.project.gym.domain.AdminDetails;
import com.project.gym.domain.MembershipSuspendHistory;
import com.project.gym.service.MembershipService;
import com.project.gym.service.MembershipSuspendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/membership/suspend")
public class MembershipSuspendController {

    private final MembershipSuspendService suspendService;
    private final MembershipService membershipService;

    public MembershipSuspendController(MembershipService membershipService,
                                       MembershipSuspendService suspendService) {
        this.membershipService = membershipService;
        this.suspendService = suspendService;
    }


    // 멤버십 정지 폼 호출
    @GetMapping("/register")
    public String showSuspendForm(@RequestParam Long membershipId, Model model){
        MembershipSuspendHistory suspendHistory = new MembershipSuspendHistory();
        suspendHistory.setMembershipId(membershipId);

        model.addAttribute("suspend", suspendHistory);

        return "membership/suspend/register";
    }

    // 멤버십 정지 추가 처리
    @PostMapping("/register")
    public String registerSuspend(@ModelAttribute MembershipSuspendHistory suspend,
                                  @AuthenticationPrincipal AdminDetails adminDetails) {
        suspend.setCreatedBy(adminDetails.getAdmin().getAdminId());
        suspendService.addSuspendHistory(suspend);

        System.out.println("************* suspend:" + suspend);

        return "membership/list";
    }

    // 멤버십 목록
    @GetMapping("/list")
    public String showSuspendList(Model model) {
        List<MembershipSuspendHistory> suspendList = suspendService.getAllSuspendHistories();

        model.addAttribute("suspendList", suspendList);

        return "membership/suspend/list";
    }


}
