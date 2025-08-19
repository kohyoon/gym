package com.project.gym.controller;

import com.project.gym.domain.AdminDetails;
import com.project.gym.domain.Membership;
import com.project.gym.domain.MembershipSuspendHistory;
import com.project.gym.dto.membership.suspend.SuspendCreateFormDTO;
import com.project.gym.dto.membership.suspend.SuspendDetailDTO;
import com.project.gym.service.MembershipService;
import com.project.gym.service.MembershipSuspendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/membership/suspend")
public class MembershipSuspendController {

    private final MembershipSuspendService suspendService;
    private final MembershipService membershipService;

    //===== 멤버십 정지 폼 호출 =====//
    @GetMapping("/register")
    public String showSuspendForm(@RequestParam Long membershipId,
                                  @AuthenticationPrincipal AdminDetails adminDetails,
                                  Model model){
        SuspendCreateFormDTO dto = new SuspendCreateFormDTO();
        dto.setMembershipId(membershipId);
        dto.setCreatedBy(adminDetails.getAdmin().getAdminId());

        Membership membership = membershipService.findById(membershipId);

        model.addAttribute("suspend", dto);
        model.addAttribute("membership", membership);

        return "membership/suspend/register";
    }

    //===== 멤버십 정지 추가 처리 =====//
    @PostMapping("/register")
    public String registerSuspend(SuspendCreateFormDTO dto,
                                  @AuthenticationPrincipal AdminDetails adminDetails) {
        // 관리자 ID
        dto.setCreatedBy(adminDetails.getAdmin().getAdminId());

        // 정지 이력 등록
        suspendService.insertSuspendHistory(dto);

        Membership membership = new Membership();
        membership.setMembershipId(dto.getMembershipId());
        membership.setExtendedEndDate(dto.getExtendedEndDate());
        membership.setUpdatedBy(adminDetails.getAdmin().getAdminId());
        membershipService.updateStatusAndExtendedEndDate(membership);

        return "redirect:/membership/detail/" + dto.getMembershipId();
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
        SuspendDetailDTO dto = suspendService.getSuspendDetailById(suspendId);
        model.addAttribute("suspend", dto);

        return "membership/suspend/detail";
    }


}
