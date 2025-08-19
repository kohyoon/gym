package com.project.gym.controller;

import com.project.gym.common.PageResult;
import com.project.gym.domain.Member;
import com.project.gym.domain.MemberDetails;
import com.project.gym.dto.member.*;
import com.project.gym.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //===== 회원 목록 조회 =====//
    @GetMapping("/admin/members")
    public String showMemberLists(@ModelAttribute("cond") MemberSearchCondition cond, Model model) {
        PageResult<MemberListDTO> page = memberService.getMemberPage(cond);
        model.addAttribute("page", page);
        model.addAttribute("members", page.content());
        model.addAttribute("cond", cond);

        return "member/list";
    }

    //===== 회원 상세 =====//
    @GetMapping("/admin/member/detail/{id}")
    public String showMemberDetail(@PathVariable("id") Long memberId, Model model) {
        Member member = memberService.getById(memberId);
        if(member == null) {
            model.addAttribute("message", "해당 회원을 찾을 수 없습니다.");
            return "redirect:/member/list";
        }
        model.addAttribute("member", member);
        return "member/detail";
    }

    //===== (자기 자신) 회원 상세 =====//
    @GetMapping("/member/my")
    public String showMyDetail(@AuthenticationPrincipal MemberDetails me, Model model) {
        Long memberId = me.getMember().getMemberId();
        MemberDetailResponseDTO dto = memberService.getMemberDetail(memberId);
        model.addAttribute("member", dto);
        return "member/detail";
    }


    //===== 회원 수정 폼 호출 =====//
    @GetMapping("/member/edit/{id}")
    public String showMemberEditForm (@PathVariable("id") Long memberId, Model model) {
        Member member = memberService.getById(memberId);
        MemberUpdateFormDTO form = new MemberUpdateFormDTO();
        form.setMemberId(member.getMemberId());
        form.setMemberLoginId(member.getMemberLoginId());
        form.setMemberName(member.getMemberName());
        form.setPhone(member.getPhone());
        form.setEmail(member.getEmail());
        form.setGender(member.getGender());
        form.setBirthDate(member.getBirthDate());

        model.addAttribute("memberForm", form);
        return "member/edit";
    }

    //===== 회원 수정 처리 =====//
    @PostMapping("/member/edit")
    public String updateMember(@Valid @ModelAttribute("memberForm") MemberUpdateFormDTO form,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        // 이메일 변경 시 중복 검사
        if(!bindingResult.hasFieldErrors("email")
                && memberService.existsOtherUserByEmail(form.getMemberId(), form.getEmail())) {
            bindingResult.rejectValue("email", "duplicate", "이미 사용 중인 이메일입니다.");
        }

        try{
            memberService.updateMember(form);
        } catch (IllegalArgumentException e) {
            bindingResult.reject("notFound", e.getMessage());
        } catch (SecurityException e) {
            bindingResult.rejectValue("currentPassword", "mismatch", "현재 비밀번호와 일치하지 않습니다.");
        }

        if(bindingResult.hasErrors()) {
            return "member/edit";
        }

        redirectAttributes.addFlashAttribute("message", "회원 정보가 수정되었습니다.");
        return "redirect:/member/my";


    }

    //===== 회원 탈퇴 처리 =====//
    @PostMapping("/member/withdraw")
    public String handleDelete (@AuthenticationPrincipal MemberDetails memberDetails) {

        Long memberId = memberDetails.getMember().getMemberId();
        memberService.withdrawMember(memberId); // 본인 탈퇴 처리
        SecurityContextHolder.clearContext(); // 세션 초기화(로그아웃)

        return "redirect:/auth/member_login?withdraw=true";
    }


    //===== 회원 검색 =====//
    @GetMapping("/admin/member/search")
    public String searchMemberList(@ModelAttribute MemberSearchCondition cond, Model model) {
        PageResult<MemberListDTO> page = memberService.getMemberPage(cond);
        model.addAttribute("page", page);
        model.addAttribute("members", page.content());
        model.addAttribute("cond", cond);
        return "member/search";
    }



}
