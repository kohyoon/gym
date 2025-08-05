package com.project.gym.controller;

import com.project.gym.domain.AdminDetails;
import com.project.gym.domain.MemberDetails;
import com.project.gym.domain.Membership;
import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.service.MembershipRefundService;
import com.project.gym.service.MembershipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/membership")
public class MembershipController {

    private final MembershipService membershipService;
    private final MembershipRefundService refundService;

    public MembershipController(MembershipService membershipService,
                                MembershipRefundService refundService) {
        this.membershipService = membershipService;
        this.refundService = refundService;
    }

    //===== 회원권 등록 폼 호출 =====//
    @GetMapping("/register")
    public String showMembershipRegisterForm(@AuthenticationPrincipal AdminDetails adminDetails, Model model) {
        Membership membership = new Membership();

        // 로그인한 ADMIN_ID
        Long adminId = adminDetails.getAdmin().getAdminId();
        membership.setCreatedBy(adminId);

        model.addAttribute("membership", membership);
        return "membership/register";
    }

    //===== 회원권 등록 처리 =====//
    @PostMapping("/register")
    public String registerMembership(@ModelAttribute Membership membership,
                                     @AuthenticationPrincipal AdminDetails adminDetails) {

        // 로그인한 관리자 ID가 세팅되지 않으면 오류 발생
        if (adminDetails == null) {
            throw new IllegalStateException("관리자 로그인 정보가 없습니다.");
        }

        // createdBy 값 직접 세팅
        membership.setCreatedBy(adminDetails.getAdmin().getAdminId());

        membershipService.registerMembership(membership);
        return "redirect:/member/list"; //등록 후 목록으로
    }

    //===== 회원권 전체 목록 =====//
    @GetMapping("/list")
    public String showMembershipListPage(Model model) {
        List<Membership> membershipList = membershipService.findAllMemberships();
        model.addAttribute("membershipList", membershipList);
        return "membership/list";
    }

    //===== 회원권 전체 목록 =====//
    @GetMapping("/list/{id}")
    public String viewMyMemberships(@AuthenticationPrincipal MemberDetails memberDetails, Model model) {
        // 로그인한 멤버 정보
        Long memberId = memberDetails.getMember().getMemberId();

        // 회원 ID에 의한 회원권 조회
        List<Membership> memberships = membershipService.getMembershipsByMemberId(memberId);

        model.addAttribute("membershipList", memberships);

        return "membership/list";
    }

    //===== 회원권 상세 페이지 =====//
    @GetMapping("/detail/{id}")
    public String showMembershipDetailPage(@PathVariable("id") Long membershipId, Model model) {
        Membership membership = membershipService.findById(membershipId);

        // 환불 내역 표시
//        MembershipRefundHistory refundHistory = refundService.getRefundByMembershipId(membershipId);

        model.addAttribute("membership", membership);
//        model.addAttribute("refundHistory", refundHistory);

        return "membership/detail";
    }

    //===== 회원권 수정 폼 호출 =====//
    @GetMapping("/edit/{id}")
    public String showMembershipEditForm(@PathVariable("id") Long membershipId, Model model) {
        Membership membership = membershipService.findById(membershipId);
        model.addAttribute("membership",membership);
        return "membership/edit";
    }

    //===== 회원권 수정 처리 =====//
    @PostMapping("/edit")
    public String editMembership(@ModelAttribute Membership membership, RedirectAttributes redirectAttributes) {

        log.info("membership: {}", membership);

        membershipService.updateMembership(membership);

        redirectAttributes.addFlashAttribute("editMessage", true);

        return "redirect:/membership/detail/" + membership.getMembershipId();
    }



}
