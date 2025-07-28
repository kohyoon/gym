package com.project.gym.service;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.MembershipRefundLog;
import com.project.gym.mapper.MembershipRefundMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MembershipRefundService {

    // 환불 요청
    void createRefundRequest(MembershipRefundHistory refundHistory);

    // 환불 요청 로그 저장
    void createRefundLog(MembershipRefundLog refundLog);

    // 전체 환불 내역 조회
    List<MembershipRefundHistory> getAllRefundHistories();

    // 멤버십 번호로 환불 내역 조회
    MembershipRefundHistory getRefundByMembershipId(Long membershipId);

    // 키워드(memberName, membershipType)로 환불 내역 검색
    List<MembershipRefundHistory> searchRefundsByKeyword(String keyword);

    // 환불 상세페이지 - 환불ID로 환불 내역 조회
    MembershipRefundHistory getRefundDetailById(Long refundId);

    // 환불 상세페이지 - 로그 목록
    List<MembershipRefundLog> getRefundLogsByRefundId(Long refundId);

}
