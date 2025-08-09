package com.project.gym.controller;

import com.project.gym.domain.*;
import com.project.gym.domain.enums.ActorRole;
import com.project.gym.dto.membership.refund.RefundDetailDTO;
import com.project.gym.dto.membership.refund.RefundListDTO;
import com.project.gym.dto.membership.refund.RefundRequestDTO;
import com.project.gym.service.MemberService;
import com.project.gym.service.MembershipRefundService;
import com.project.gym.service.MembershipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class MembershipRefundController {

    private final MembershipRefundService refundService;
    private final MemberService memberService;
    private final MembershipService membershipService;


    public MembershipRefundController(MemberService memberService,
                                      MembershipService membershipService,
                                      MembershipRefundService refundService) {
        this.memberService = memberService;
        this.membershipService = membershipService;
        this.refundService = refundService;
    }

    //===== 환불폼 호출 =====//
    @GetMapping("/membership/refund/request")
    public String showRefundRequestForm(@AuthenticationPrincipal MemberDetails memberDetails, Long membershipId, Model model) {
        // 회원권 정보 조회
        Membership membership = membershipService.findById(membershipId);

        // DTO 생성 및 세팅
        RefundRequestDTO dto = new RefundRequestDTO();
        dto.setMembershipId(membershipId);
        //dto.setMemberName(membership);
        dto.setMembershipType(membership.getMembershipType());
        dto.setMembershipPrice(membership.getPrice());
        dto.setStartDate(membership.getStartDate());
        dto.setEndDate(membership.getEndDate());
        dto.setPeriodDays(membership.getPeriodDays());

        model.addAttribute("refund", dto);

        return "membership/refund/request";
    }


    //===== 환불 등록 처리 =====//
    @PostMapping("/membership/refund/request")
    public String submitRefundRequest(@AuthenticationPrincipal MemberDetails memberDetails,
                                      MembershipRefundHistory refund) {
        // 로그인 정보 저장
        refund.setRequestedBy(memberDetails.getMember().getMemberId());

        refundService.requestRefund(refund);

        return "membership/refund/member_list";
    }

    //===== 환불 목록 - 관리자 =====//
    @GetMapping("/admin/refund/list")
    public String adminRefundList(@RequestParam(required = false) String searchType,
                                  @RequestParam(required = false) String keyword,
                                  Model model) {
        List<RefundListDTO> list = refundService.getRefundListByKeyword(searchType, keyword, ActorRole.ADMIN, null);

        model.addAttribute("refundList", list);

        return "membership/refund/admin_list";
    }

    //===== 환불 목록 - 회원 =====//
    @GetMapping("/member/refund/list")
    public String memberRefundList(@AuthenticationPrincipal MemberDetails memberDetails,
                                   @RequestParam(required = false) String searchType,
                                   @RequestParam(required = false) String keyword,
                                   Model model) {
        List<RefundListDTO> list = refundService.getRefundListByKeyword(searchType, keyword, ActorRole.MEMBER, memberDetails.getMember().getMemberId());

        model.addAttribute("refundList", list);

        return "membership/refund/member_list";
    }

    //===== 환불 상세 - 관리자 =====//
    @GetMapping("/admin/refund/detail/{id}")
    public String viewRefundDetailForAdmin(@PathVariable("id") Long refundId, Model model) {
        ActorRole actorRole = ActorRole.ADMIN;

        // 환불 정보
        RefundDetailDTO dto = refundService.getRefundDetail(refundId, actorRole);
        model.addAttribute("refund", dto);

        // 회원권 정보
        Long membershipId = dto.getMembershipId();
        Membership membership = membershipService.findById(membershipId);
        model.addAttribute("membership", membership);

        return "membership/refund/detail";
    }

    //===== 환불 상세 - 회원 =====//
    @GetMapping("/member/refund/detail/{id}")
    public String viewRefundDetailForMember(@PathVariable("id") Long refundId, Model model) {
        ActorRole actorRole = ActorRole.MEMBER;

        // 환불 정보
        RefundDetailDTO dto = refundService.getRefundDetail(refundId, actorRole);
        model.addAttribute("refund", dto);

        // 회원권 정보
        Long membershipId = dto.getMembershipId();
        Membership membership = membershipService.findById(membershipId);
        model.addAttribute("membership", membership);

        return "membership/refund/detail";
    }

    //===== 환불 상태 - PENDING - 관리자 only =====//
    @PostMapping("/admin/refund/pending/{id}")
    public String markRefundAsPending(@PathVariable("id") Long refundId,
                                      RedirectAttributes redirectAttributes,
                                      @AuthenticationPrincipal AdminDetails adminDetails) {
        // 로그인 정보 없을 경우
        if(adminDetails == null) {
            throw new AccessDeniedException("로그인 필요");
        }

        refundService.markRefundAsPending(refundId, adminDetails.getAdmin().getAdminId());

        redirectAttributes.addAttribute("message", "환불 상태가 검토중으로 수정되었습니다.");

        return "redirect:/admin/refund/detail/" + refundId;
    }
}
