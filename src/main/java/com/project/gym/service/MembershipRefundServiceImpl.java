package com.project.gym.service;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.MembershipRefundLog;
import com.project.gym.mapper.MembershipRefundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MembershipRefundServiceImpl implements MembershipRefundService{

    private final MembershipRefundMapper refundMapper;

    @Autowired
    public MembershipRefundServiceImpl(MembershipRefundMapper refundMapper) {
        this.refundMapper = refundMapper;
    }


    @Override
    @Transactional
    public void requestRefund(MembershipRefundHistory refundHistory) {
        refundMapper.insertRefundRequestHistory(refundHistory);
    }

    @Override
    public void logRefundRequest(MembershipRefundLog refundLog) {
        refundMapper.insertRefundRequestLog(refundLog);
    }
}
