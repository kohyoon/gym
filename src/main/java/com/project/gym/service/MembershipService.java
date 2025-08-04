package com.project.gym.service;

import com.project.gym.domain.Membership;

import java.util.List;

public interface MembershipService {

    void registerMembership(Membership membership);

    List<Membership> findAllMemberships();

    List<Membership> getMembershipsByMemberId(Long memberId);

    Membership findById(Long id);

    void updateMembership(Membership membership);

    void markExpiredMemberships();

    List<Membership> getActiveMemberships();

    void updateStatusAndExtendedEndDate(Membership membership);
}