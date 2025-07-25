package com.project.gym.service;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.MembershipRefundLog;
import com.project.gym.mapper.MembershipRefundMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MembershipRefundService {

    // 환불 요청
    void requestRefund(MembershipRefundHistory refundHistory);

    // 환불 요청 로그 저장
    void logRefundRequest(MembershipRefundLog refundLog);

    // 전체 환불 내역 조회
    List<MembershipRefundHistory> getAllMembershipRefundList();

    // 멤버십 번호로 환불 내역 조회
    MembershipRefundHistory getRefundByMembershipId(Long membershipId);

}
