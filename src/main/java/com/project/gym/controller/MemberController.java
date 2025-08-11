package com.project.gym.controller;

import com.project.gym.domain.Member;
import com.project.gym.domain.MemberDetails;
import com.project.gym.dto.member.MemberCreateFormDTO;
import com.project.gym.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController (MemberService memberService) {
        this.memberService = memberService;
    }

    //===== 회원 등록 폼 호출 =====//
    @GetMapping("/member/signup")
    public String showRegisterForm(Model model) {
        model.addAttribute("memberForm", new MemberCreateFormDTO());
        return "member/signup";
    }

    //===== 로그인 아이디 중복 확인 =====//
    @GetMapping("/member/check-loginId")
    @ResponseBody
    public boolean checkLoginId(@RequestParam String memberLoginId) {
        return !memberService.existsByLoginId(memberLoginId);
    }

    //===== 이메일 중복 확인 =====//
    @GetMapping("/member/check-email")
    @ResponseBody
    public boolean checkEmail(@RequestParam String email) {
        return !memberService.existsByEmail(email);
    }

    //===== 회원 등록 처리 =====//
    @PostMapping("/member/signup")
    public String registerMember (@Valid @ModelAttribute("memberForm") MemberCreateFormDTO form,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(!form.getMemberPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "passwordMismatch", "비밀번호가 일치하지 않습니다.");
        }
        if(bindingResult.hasErrors()) {
            return "member/signup";
        }

        memberService.registerMember(form);
        redirectAttributes.addFlashAttribute("message", "회원 등록이 완료되었습니다.");

        return "redirect:/auth/member_login";
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
