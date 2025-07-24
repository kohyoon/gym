package com.project.gym.service;

import com.project.gym.domain.Membership;

import java.util.List;

public interface MembershipService {

    void insertMembership(Membership membership);

    List<Membership> getAllMemberships();

    Membership findByMembershipId(Long id);

    void updateMembership(Membership membership);

    void updateExpiredMemberships();

    List<Membership> getActiveMemberships();
}