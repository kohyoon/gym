package com.project.gym.controller;

import com.project.gym.domain.AdminDetails;
import com.project.gym.domain.Membership;
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

        Membership membership = membershipService.findById(membershipId);

        model.addAttribute("suspend", suspendHistory);
        model.addAttribute("membership", membership);

        return "membership/suspend/register";
    }

    // 멤버십 정지 추가 처리
    @PostMapping("/register")
    public String registerSuspend(@ModelAttribute MembershipSuspendHistory suspend,
                                  @AuthenticationPrincipal AdminDetails adminDetails) {
        // 관리자 ID
        suspend.setCreatedBy(adminDetails.getAdmin().getAdminId());

        // 정지 이력 등록
        suspendService.addSuspendHistory(suspend);

        Membership membership = new Membership();
        membership.setMembershipId(suspend.getMembershipId());
        membership.setExtendedEndDate(suspend.getExtendedEndDate());
        membership.setUpdatedBy(adminDetails.getAdmin().getAdminId());

        membershipService.updateStatusAndExtendedEndDate(membership);



        return "membership/list";
    }

    // 정지 목록
    @GetMapping("/list")
    public String showSuspendList(Model model) {
        List<MembershipSuspendHistory> suspendList = suspendService.getAllSuspendHistories();

        model.addAttribute("suspendList", suspendList);

        return "membership/suspend/list";
    }

    // 정지 목록 - by MembershipId
    @GetMapping("/search")
    public String suspendListByMembership(@PathVariable Long membershipId, Model model) {
        List<MembershipSuspendHistory> suspendList = suspendService.getSuspendHistoriesByMembershipId(membershipId);

        model.addAttribute("suspend", suspendList);

        return "membership/suspend/search";
    }

    // 정지 상세
    @GetMapping("/detail/{id}")
    public String suspendDetail(@PathVariable("id") Long suspendId, Model model){
        MembershipSuspendHistory suspend = suspendService.getSuspendHistoryById(suspendId);
        Membership membership = membershipService.findById(suspend.getMembershipId());
        suspend.setPeriodDays(membership.getPeriodDays());

        model.addAttribute("suspend", suspend);
        model.addAttribute("membership", membership);

        return "membership/suspend/detail";
    }


}
