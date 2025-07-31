package com.project.gym.service;

import com.project.gym.domain.MembershipSuspendHistory;

public interface MembershipSuspendService {

    void addSuspendHistory(MembershipSuspendHistory suspendHistory);
}
