package com.project.gym.controller;

import com.project.gym.domain.Member;
import com.project.gym.domain.Membership;
import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.MembershipRefundLog;
import com.project.gym.service.MemberService;
import com.project.gym.service.MembershipRefundService;
import com.project.gym.service.MembershipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/membership/refund")
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

    // 환불 요청 폼 호출
    @GetMapping("/request")
    public String showRefundRequestForm(@RequestParam("membershipId") Long membershipId, Model model) {
        // 1. 회원권 정보 조회
        Membership membership = membershipService.findById(membershipId);
        Member member = memberService.getMemberById(membership.getMemberId());

        // 2. form 객체 구성
        MembershipRefundHistory refundHistory = new MembershipRefundHistory();
        refundHistory.setMembershipId(membershipId);

        model.addAttribute("refundHistory", refundHistory);
        model.addAttribute("membership", membership);
        model.addAttribute("member", member);

        return "membership/refund/request";
    }


    // 환불 요청 처리
    @PostMapping("/request")
    public String handleRefundRequest (@ModelAttribute("refundHistory") MembershipRefundHistory refundHistory,
                                      Model model) {

        try{
            // 1. 환불 요청 저장
            refundHistory.setRequestedBy("admin"); // 임시
            refundService.createRefundRequest(refundHistory);

            // 2. 로그 저장
            MembershipRefundLog refundLog = new MembershipRefundLog();
            refundLog.setRefundId(refundHistory.getRefundId());
            refundLog.setActionType(0); // 0 요청
            refundLog.setActionDetail("환불 요청 등록됨");
            refundLog.setActionBy("admin"); // 임시

            refundService.createRefundLog(refundLog);

            // 3. 성공 메시지 및 리다이렉트
            model.addAttribute("successMessage", "환불 요청이 등록되었습니다.");
            return "redirect:/membership/detail/" + refundHistory.getMembershipId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", "환불 요청 처리 중 오류가 발생했습니다.");
            return "membership/refund/request";
        }

    }

    // 전체 환불 내역
    @GetMapping("/list")
    public String showRefundList(Model model) {
        List<MembershipRefundHistory> refundList = refundService.getAllRefundHistories();

        model.addAttribute("refundList", refundList);

        return "membership/refund/list";
    }

    // 키워드로 환불 내역 검색
    @GetMapping("/search")
    public String searchRefunds(@RequestParam(required = false) String keyword,
                                   Model model){

        if (keyword == null) {
            keyword = "";
        }

        List<MembershipRefundHistory> refundList = refundService.searchRefundsByKeyword(keyword);
        model.addAttribute("refundList", refundList);
        model.addAttribute("keyword", keyword);

        return "membership/refund/list";
    }

    // 환불 상세페이지
    @GetMapping("/detail/{id}")
    public String viewRefundDetail (@PathVariable("id") Long refundId, Model model){

        MembershipRefundHistory refund = refundService.getRefundDetailById(refundId);

        Membership membership = membershipService.findById(refund.getMembershipId());
        List<MembershipRefundLog> refundLogs = refundService.getRefundLogsByRefundId(refundId);


        model.addAttribute("refund", refund);
        model.addAttribute("refundLogs", refundLogs);
        model.addAttribute("membership", membership);

        return "membership/refund/detail";
    }



}
