package com.project.gym.service;

import com.project.gym.domain.Membership;
import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.MembershipRefundLog;
import com.project.gym.domain.enums.ActorRole;
import com.project.gym.domain.enums.RefundLogType;
import com.project.gym.dto.membership.refund.RefundDetailDTO;
import com.project.gym.dto.membership.refund.RefundListDTO;
import com.project.gym.dto.membership.refund.RefundLogDTO;
import com.project.gym.mapper.MembershipMapper;
import com.project.gym.mapper.MembershipRefundLogMapper;
import com.project.gym.mapper.MembershipRefundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipRefundServiceImpl implements MembershipRefundService{

    private final MembershipRefundMapper refundMapper;
    private final MembershipRefundLogMapper logMapper;
    private final MembershipMapper membershipMapper;

    // 환불 등록
    @Override
    @Transactional
    public void requestRefund(MembershipRefundHistory refund) {
        // 환불 이력등록
        refundMapper.insertRefund(refund);

        // 로그 작성
        MembershipRefundLog log = MembershipRefundLog.from(
                refund, RefundLogType.REQUESTED, ActorRole.MEMBER, refund.getRequestedBy()
        );

        logMapper.insertRefundLog(log);
    }

    @Override
    public List<RefundListDTO> getRefundListByKeyword(String searchType, String keyword, ActorRole actorRole, Long memberId) {
        return refundMapper.selectRefundListByKeyword(searchType, keyword, actorRole.name(), memberId);
    }

    @Override
    public RefundDetailDTO getRefundDetail(Long refundId, ActorRole actorRole) {
        // Enum -> String 변환
        String role = actorRole.name();

        // 환불 정보
        RefundDetailDTO dto = refundMapper.selectRefundDetailById(refundId);

        // 로그 정보
        List<RefundLogDTO> logs = logMapper.selectLogsByRefundId(refundId, role);
        dto.setLogs(logs);

        return dto;
    }

    @Override
    public void markRefundAsPending(Long refundId, Long adminId) {

        // refund 정보 저장
        MembershipRefundHistory refund = refundMapper.selectRefundHistoryById(refundId);

        refund.setReviewedBy(adminId);

        // 로그 정보 저장
        MembershipRefundLog log = MembershipRefundLog.from(
                refund, RefundLogType.PENDING, ActorRole.ADMIN, refund.getReviewedBy()
        );


        // 환불 상태 업데이트
        refundMapper.updateRefundStatusToPending(refundId, adminId);

        // 로그 추가
        logMapper.insertRefundLog(log);
    }

    @Override
    public void markRefundAsApproved(Long refundId, Long adminId) {

        // refund 정보 저장
        MembershipRefundHistory refund = refundMapper.selectRefundHistoryById(refundId);

        refund.setApprovedBy(adminId);

        // 1. 남은 기간 계산
        Membership membership = membershipMapper.findById(refund.getMembershipId());
        LocalDate endDate = membership.getEndDate();
        LocalDate requestDate = refund.getRequestedAt();

        int remainingDays = (int) ChronoUnit.DAYS.between(requestDate, endDate);

        // 2. 이용권 기간, 이용권 가격
        int periodDays = membership.getPeriodDays();
        int membershipPrice = membership.getPrice();

        // 3. 환불 금액 = (남은 기간 / 이용권 기간) * 이용권 결제 금액
        int refundAmount = (int) Math.round(((double) remainingDays / periodDays) * membershipPrice);
        
        // 음수인 경우 조건 처리해야함

        refund.setRefundAmount(refundAmount);

        // 로그 정보 저장
        MembershipRefundLog log = MembershipRefundLog.from(
                refund, RefundLogType.APPROVED, ActorRole.ADMIN, refund.getApprovedBy()
        );

        // 환불 상태 업데이트
        refundMapper.updateRefundStatusToApproved(refundId, adminId, refundAmount);

        // 로그 추가
        logMapper.insertRefundLog(log);

        // 회원권 테이블 업데이트
        membershipMapper.refundMembership(refund.getMembershipId(), refund.getApprovedBy());
    }

    @Override
    public void markRefundAsRejected(Long refundId, Long adminId, String rejectReason) {

        // refund 정보 저장
        MembershipRefundHistory refund = refundMapper.selectRefundHistoryById(refundId);

        refund.setRejectedBy(adminId);
        refund.setRejectReason(rejectReason);

        // 로그 정보 저장
        MembershipRefundLog log = MembershipRefundLog.from(
                refund, RefundLogType.REJECTED, ActorRole.ADMIN, refund.getRejectedBy()
        );

        System.out.println("********** refund:" + refund);
        System.out.println("********** log:" + log);

        // 환불 상태 업데이트
        refundMapper.updateRefundStatusToRejected(refundId, adminId, refund.getRejectReason());

        // 로그 추가
        logMapper.insertRefundLog(log);

    }


}
