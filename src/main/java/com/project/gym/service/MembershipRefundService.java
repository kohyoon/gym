package com.project.gym.service;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.enums.ActorRole;
import com.project.gym.dto.refund.RefundDetailDTO;
import com.project.gym.dto.refund.RefundListDTO;

import java.util.List;

public interface MembershipRefundService {

    // 환불 등록
    void requestRefund(MembershipRefundHistory refund);

    // 환불 목록
    List<RefundListDTO> getRefundListByKeyword(String searchType,
                                               String keyword,
                                               ActorRole actorRole,
                                               Long memberId);

    // 환불 내역 조회 - by MembershipId
    RefundListDTO getRefundByMembershipId(Long membershipId);

    // 환불 상세
    RefundDetailDTO getRefundDetail(Long refundId, ActorRole actorRole);

    // 환불 상태 - 검토중(PENDING)으로 수정
    void markRefundAsPending(Long refundId, Long adminId);

    // 환불 상태 - 승인(APPROVED)으로 수정
    void markRefundAsApproved(Long refundId, Long adminId);

    // 환불 상태 - 반려(REJECTED)으로 수정
    void markRefundAsRejected(Long refundId, Long adminId, String rejectReason);

}
