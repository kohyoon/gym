package com.project.gym.controller;

import com.project.gym.domain.AdminDetails;
import com.project.gym.domain.MemberDetails;
import com.project.gym.dto.membership.*;
import com.project.gym.dto.suspend.SuspendListDTO;
import com.project.gym.service.MembershipService;
import com.project.gym.service.MembershipSuspendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;
    private final MembershipSuspendService suspendService;

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

        return "membership/admin_list";
    }

    //===== (자신의) 회원권 목록 =====//
    @GetMapping("/member/membership/my")
    public String showMyMembershipListPage(@ModelAttribute("criteria") MembershipSearchCriteria criteria,
                                           @AuthenticationPrincipal MemberDetails memberDetails,
                                           Model model) {
        // 로그인한 멤버 정보
        criteria.setMemberId(memberDetails.getMemberId());

        // 회원 ID에 의한 회원권 조회
        List<MembershipListDTO> dtos = membershipService.findAllMemberships(criteria);
        int total = membershipService.countMemberships(criteria);

        // 페이지 계산
        int page = criteria.getPage() == null ? 0 : criteria.getPage();
        int size = criteria.getSize() == null ? 20 : criteria.getSize();
        long totalPages = (size <= 0) ? 0 : (long) Math.ceil((double) total / (double) size);

        model.addAttribute("criteria", criteria);
        model.addAttribute("membershipList", dtos);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("total", total);
        model.addAttribute("totalPages", totalPages);

        return "membership/my_list";
    }

    //===== 회원권 상세 페이지 =====//
    @GetMapping("/membership/detail/{id}")
    public String showMembershipDetailPage(@PathVariable("id") Long membershipId, Model model) {
        MembershipDetailDTO detail = membershipService.getMembershipDetail(membershipId);
        model.addAttribute("membership", detail);
        System.out.println("******** membership:" + detail);
        
        // 정지 내역
        List<SuspendListDTO> suspend = suspendService.getSuspendDTOByMembershipId(membershipId);
        model.addAttribute("suspend", suspend);
        System.out.println("********** suspend:" + suspend);
        
        // 환불 내역 표시
//        MembershipRefundHistory refundHistory = refundService.getRefundByMembershipId(membershipId);
//        model.addAttribute("refundHistory", refundHistory);

        return "membership/detail";
    }

    //===== 회원권 수정 폼 호출 =====//
    @GetMapping("/membership/edit/{id}")
    public String showMembershipEditForm(@PathVariable("id") Long membershipId,
                                         @AuthenticationPrincipal AdminDetails adminDetails,
                                         Model model) {

        // 관리자 정보
        Long adminId = adminDetails.getAdmin().getAdminId();
        MembershipUpdateFormDTO dto = membershipService.getMembershipEditForm(membershipId, adminId);

        model.addAttribute("membership",dto);
        return "membership/edit";
    }

    //===== 회원권 수정 처리 =====//
    @PostMapping("/membership/edit")
    public String editMembership(@ModelAttribute @Valid MembershipUpdateFormDTO dto,
                                 @AuthenticationPrincipal AdminDetails adminDetails) {

        dto.setUpdatedBy(adminDetails.getAdmin().getAdminId());

        membershipService.updateMembership(dto);

        //redirectAttributes.addFlashAttribute("editMessage", true);

        return "redirect:/membership/detail/" + dto.getMembershipId();
    }



}
