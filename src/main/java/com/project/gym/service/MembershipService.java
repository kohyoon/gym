package com.project.gym.service;

import com.project.gym.domain.Member;
import com.project.gym.domain.Membership;
import com.project.gym.mapper.MemberMapper;
import com.project.gym.mapper.MembershipMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MembershipService {

    private final MembershipMapper membershipMapper;

    public MembershipService (MembershipMapper membershipMapper) {
        this.membershipMapper = membershipMapper;
    }

    @Transactional
    public void insertMembership(Membership membership) {
        // 종료날짜 계산 = 시작날짜 + 이용권 기간 - 1
        membership.setEndDate(membership.getStartDate().plusDays(membership.getPeriodDays() - 1));
        membershipMapper.insertMembership(membership);
    }




}
