package com.project.gym.controller;

import com.project.gym.domain.AdminDetails;
import com.project.gym.domain.MemberDetails;
import com.project.gym.domain.Membership;
import com.project.gym.dto.membership.MembershipCreateFormDTO;
import com.project.gym.dto.membership.MembershipListDTO;
import com.project.gym.dto.membership.MembershipSearchCriteria;
import com.project.gym.service.MembershipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    //===== 회원권 등록 폼 호출 =====//
    @GetMapping("/membership/register")
    public String showMembershipRegisterForm(@ModelAttribute("form") MembershipCreateFormDTO form,
                                             @AuthenticationPrincipal AdminDetails adminDetails, Model model) {

        // 로그인한 ADMIN_ID
        Long adminId = adminDetails.getAdmin().getAdminId();
        form.setCreatedBy(adminId);

        model.addAttribute("form", form);
        return "membership/register";
    }

    //===== 회원권 등록 처리 =====//
    @PostMapping("/membership/register")
    public String registerMembership(@ModelAttribute("form") MembershipCreateFormDTO form,
                                     BindingResult bindingResult,
                                     @AuthenticationPrincipal AdminDetails adminDetails) {

        // 로그인한 관리자 ID가 세팅되지 않으면 오류 발생
        if (adminDetails == null) {
            bindingResult.reject("auth.required", "관리자 로그인이 필요합니다.");
        } else {
            form.setCreatedBy(adminDetails.getAdmin().getAdminId());
        }

        if(bindingResult.hasErrors()) {
            return "membership/register";
        }

        membershipService.registerMembership(form);
        
        return "redirect:/member/list"; // 임시
    }

    //===== 회원권 목록 조회 =====//
    @GetMapping("/admin/membership/list")
    public String showMembershipListPage(@ModelAttribute("criteria") MembershipSearchCriteria criteria,
                                         Model model) {
        List<MembershipListDTO> row = membershipService.findAllMemberships(criteria);
        int total = membershipService.countMemberships(criteria);

        int page = criteria.getPage() == null ? 0 : criteria.getPage();
        int size = criteria.getSize() == null ? 20 : criteria.getSize();
        long totalPages = (size <= 0) ? 0 : (long) Math.ceil((double) total / (double) size);

        model.addAttribute("membershipList", row);
        model.addAttribute("total", total);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", totalPages);

        return "membership/list";
    }

    //===== (자신의) 회원권 목록 =====//
    @GetMapping("/membership/list/{id}")
    public String viewMyMemberships(@AuthenticationPrincipal MemberDetails memberDetails, Model model) {
        // 로그인한 멤버 정보
        Long memberId = memberDetails.getMember().getMemberId();

        // 회원 ID에 의한 회원권 조회
        List<Membership> memberships = membershipService.getMembershipsByMemberId(memberId);

        model.addAttribute("membershipList", memberships);

        return "membership/list";
    }

    //===== 회원권 상세 페이지 =====//
    @GetMapping("/membership/detail/{id}")
    public String showMembershipDetailPage(@PathVariable("id") Long membershipId, Model model) {
        Membership membership = membershipService.findById(membershipId);

        // 환불 내역 표시
//        MembershipRefundHistory refundHistory = refundService.getRefundByMembershipId(membershipId);

        model.addAttribute("membership", membership);
//        model.addAttribute("refundHistory", refundHistory);

        return "membership/detail";
    }

    //===== 회원권 수정 폼 호출 =====//
    @GetMapping("/membership/edit/{id}")
    public String showMembershipEditForm(@PathVariable("id") Long membershipId, Model model) {
        Membership membership = membershipService.findById(membershipId);
        model.addAttribute("membership",membership);
        return "membership/edit";
    }

    //===== 회원권 수정 처리 =====//
    @PostMapping("/membership/edit")
    public String editMembership(@ModelAttribute Membership membership, RedirectAttributes redirectAttributes) {

        log.info("membership: {}", membership);

        membershipService.updateMembership(membership);

        redirectAttributes.addFlashAttribute("editMessage", true);

        return "redirect:/membership/detail/" + membership.getMembershipId();
    }



}
