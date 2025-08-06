package com.project.gym.service;

import com.project.gym.domain.MembershipRefundLog;

public interface MembershipRefundLogService {

    // 로그 등록
    void writeRefundLog(MembershipRefundLog log);

}
