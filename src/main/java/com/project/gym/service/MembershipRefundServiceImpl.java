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
    public void requestRefund(MembershipRefundHistory refundHistory) {
        refundMapper.insertRefundRequestHistory(refundHistory);
    }

    @Override
    public void logRefundRequest(MembershipRefundLog refundLog) {
        refundMapper.insertRefundRequestLog(refundLog);
    }

    @Override
    public List<MembershipRefundHistory> getAllMembershipRefundList() {
        return refundMapper.selectAllRefundList();
    }

    @Override
    public MembershipRefundHistory getRefundByMembershipId(Long membershipId) {
        return refundMapper.selectRefundByMembershipId(membershipId);
    }

    @Override
    public List<MembershipRefundHistory> searchRefundList(String keyword) {
        return refundMapper.selectRefundListByKeyword(keyword);
    }

    @Override
    public MembershipRefundHistory getRefundDetailByRefundId(Long refundId) {
        return refundMapper.selectRefundByRefundId(refundId);
    }

    @Override
    public List<MembershipRefundLog> getRefundLogList(Long refundId) {
        return refundMapper.selectLogListByRefundId(refundId);
    }


}
