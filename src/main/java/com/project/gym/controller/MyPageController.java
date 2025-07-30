package com.project.gym.controller;

import com.project.gym.domain.Member;
import com.project.gym.domain.MemberDetails;
import com.project.gym.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

    @GetMapping("/member/mypage")
    public String myPage(@AuthenticationPrincipal MemberDetails memberDetails, Model model) {
        Member member = memberService.getMemberById(memberDetails.getMember().getMemberId());

        model.addAttribute("member", member);

        return "member/mypage";
    }

}
