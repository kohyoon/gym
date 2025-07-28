package com.project.gym.mapper;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.MembershipRefundLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MembershipRefundMapper {

    // 환불 신청
    void insertRefundHistory(MembershipRefundHistory refundHistory);
    void insertRefundLog(MembershipRefundLog refundLog);

    // 환불 내역
    List<MembershipRefundHistory> findAllRefunds(); // 전체 환불 내역
    List<MembershipRefundHistory> findRefundsByKeyword(String keyword); // 키워드로 검색한 환불 내역
    MembershipRefundHistory findRefundByMembershipId(Long membershipId); // 멤버십번호로 환불 내역 검색

    MembershipRefundHistory findRefundById(Long refundId); // 환불 상세 내역
    List<MembershipRefundLog> findRefundLogsByRefundId(Long refundId); // 멤버십번호에 대한 환불 로그




}
