package com.project.gym.service;

import com.project.gym.domain.Membership;
import com.project.gym.dto.membership.MembershipCreateFormDTO;

import java.util.List;

public interface MembershipService {

    // 회원권 등록
    void registerMembership(MembershipCreateFormDTO dto);

    List<Membership> findAllMemberships();

    List<Membership> getMembershipsByMemberId(Long memberId);

    Membership findById(Long id);

    void updateMembership(Membership membership);

    void markExpiredMemberships();

    List<Membership> getActiveMemberships();

    void updateStatusAndExtendedEndDate(Membership membership);
}