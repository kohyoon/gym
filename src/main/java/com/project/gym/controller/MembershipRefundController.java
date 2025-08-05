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




}
