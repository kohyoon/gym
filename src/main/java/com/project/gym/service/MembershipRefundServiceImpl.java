package com.project.gym.service;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.MembershipRefundLog;
import com.project.gym.mapper.MembershipRefundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MembershipRefundServiceImpl implements MembershipRefundService{

    private final MembershipRefundMapper refundMapper;

    @Autowired
    public MembershipRefundServiceImpl(MembershipRefundMapper refundMapper) {
        this.refundMapper = refundMapper;
    }


    @Override
    @Transactional
    public void createRefundRequest(MembershipRefundHistory refundHistory) {
        refundMapper.insertRefundHistory(refundHistory);
    }

    @Override
    public void createRefundLog(MembershipRefundLog refundLog) {
        refundMapper.insertRefundLog(refundLog);
    }

    @Override
    public List<MembershipRefundHistory> getAllRefundHistories() {
        return refundMapper.findAllRefunds();
    }

    @Override
    public MembershipRefundHistory getRefundByMembershipId(Long membershipId) {
        return refundMapper.findRefundById(membershipId);
    }

    @Override
    public List<MembershipRefundHistory> searchRefundsByKeyword(String keyword) {
        return refundMapper.findRefundsByKeyword(keyword);
    }

    @Override
    public MembershipRefundHistory getRefundDetailById(Long refundId) {
        return refundMapper.findRefundByMembershipId(refundId);
    }

    @Override
    public List<MembershipRefundLog> getRefundLogsByRefundId(Long refundId) {
        return refundMapper.findRefundLogsByRefundId(refundId);
    }


}
