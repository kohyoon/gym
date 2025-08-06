package com.project.gym.service;

import com.project.gym.domain.MembershipRefundLog;
import com.project.gym.mapper.MembershipRefundLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipRefundLogServiceImpl implements MembershipRefundLogService{

    private final MembershipRefundLogMapper logMapper;

    @Autowired
    public MembershipRefundLogServiceImpl(MembershipRefundLogMapper logMapper) {
        this.logMapper = logMapper;
    }

    // 로그 추가
    @Override
    public void writeRefundLog(MembershipRefundLog log) {
        logMapper.insertRefundLog(log);
    }
}
