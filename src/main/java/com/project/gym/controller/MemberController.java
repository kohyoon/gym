package com.project.gym.controller;

import com.project.gym.domain.Member;
import com.project.gym.domain.MemberDetails;
import com.project.gym.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController (MemberService memberService) {
        this.memberService = memberService;
    }

    //===== 회원 등록 폼 호출 =====//
    @GetMapping("/signup")
    public String showRegisterForm(Model model) {
        model.addAttribute("member", new Member());
        return "member/signup";
    }

    //===== 회원 등록 처리 =====//
    @PostMapping("/signup")
    public String handleRegister (@ModelAttribute Member member, Model model) {
        memberService.registerMember(member);
        model.addAttribute("message", "회원 등록이 완료되었습니다.");
        model.addAttribute("redirectToList", true);
        return "auth/member_login";
    }

    //===== 회원 목록 =====//
    @GetMapping("/list")
    public String listMembers(Model model) {
        List<Member> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "member/list";
    }

    //===== 회원 상세 =====//
    @GetMapping("/detail/{id}")
    public String viewMemberDetail(@PathVariable("id") Long memberId, Model model) {
        Member member = memberService.getMemberById(memberId);
        if(member == null) {
            model.addAttribute("message", "해당 회원을 찾을 수 없습니다.");
            return "redirect:/member/list";
        }
        model.addAttribute("member", member);
        return "member/detail";
    }



    //===== 회원 수정 폼 호출 =====//
    @GetMapping("/edit/{id}")
    public String showEditForm (@PathVariable("id") Long memberId, Model model) {
        Member member = memberService.getMemberById(memberId);
        model.addAttribute("member", member);
        return "member/edit";
    }

    //===== 회원 수정 처리 =====//
    @PostMapping("/edit")
    public String handleUpdate(@ModelAttribute Member member, Model model) {
        memberService.updateMember(member);
        model.addAttribute("message", "회원 정보가 수정되었습니다.");
        model.addAttribute("member", memberService.getMemberById(member.getMemberId()));
        return "redirect:member/list";
    }

    //===== 회원 탈퇴 처리 =====//
    @PostMapping("/withdraw")
    public String handleDelete (@AuthenticationPrincipal MemberDetails memberDetails) {

        Long memberId = memberDetails.getMember().getMemberId();
        memberService.withdrawMember(memberId); // 본인 탈퇴 처리
        SecurityContextHolder.clearContext(); // 세션 초기화(로그아웃)

        return "redirect:/auth/member_login?withdraw=true";
    }

    //===== 회원 목록 조회 =====//
    @GetMapping("/search")
    public String searchMembers(@RequestParam(required = false) String searchType,
                                @RequestParam(required = false) String keyword,
                                Model model) {
        if(keyword == null) {
            keyword = "";
        } if(searchType == null) {
            searchType = "all";
        }

        List<Member> members = memberService.searchMembersByKeyword(searchType, keyword);
        model.addAttribute("members", members);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
        return "member/search";
    }

}
