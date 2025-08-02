package com.project.gym.service;

import com.project.gym.domain.MembershipSuspendHistory;

import java.util.List;

public interface MembershipSuspendService {

    void addSuspendHistory(MembershipSuspendHistory suspendHistory);

    List<MembershipSuspendHistory> getAllSuspendHistories();

    List<MembershipSuspendHistory> getSuspendHistoriesByMembershipId(Long membershipId);

    List<MembershipSuspendHistory> getSuspendHistoriesByMemberId(Long memberId);

    MembershipSuspendHistory getSuspendHistoryById(Long suspendId);

}
