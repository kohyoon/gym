package com.project.gym.service;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.MembershipRefundLog;
import com.project.gym.domain.enums.ActorRole;
import com.project.gym.domain.enums.RefundLogType;
import com.project.gym.dto.membership.refund.RefundDetailDTO;
import com.project.gym.dto.membership.refund.RefundListDTO;
import com.project.gym.dto.membership.refund.RefundLogDTO;
import com.project.gym.mapper.MembershipRefundLogMapper;
import com.project.gym.mapper.MembershipRefundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipRefundServiceImpl implements MembershipRefundService{

    private final MembershipRefundMapper refundMapper;
    private final MembershipRefundLogMapper logMapper;

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


}
