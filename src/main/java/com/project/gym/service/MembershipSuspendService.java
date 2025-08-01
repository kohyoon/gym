package com.project.gym.service;

import com.project.gym.domain.MembershipSuspendHistory;

import java.util.List;

public interface MembershipSuspendService {

    void addSuspendHistory(MembershipSuspendHistory suspendHistory);

    List<MembershipSuspendHistory> getAllSuspendHistories();

}
