package com.project.gym.service;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.MembershipRefundLog;
import com.project.gym.mapper.MembershipRefundMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MembershipRefundService {

    // 환불 등록
    void registerRefundRequest(MembershipRefundHistory refund);


}
