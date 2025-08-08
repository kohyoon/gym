package com.project.gym.service;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.enums.ActorRole;
import com.project.gym.dto.membership.refund.RefundDetailDTO;
import com.project.gym.dto.membership.refund.RefundListDTO;

import java.util.List;

public interface MembershipRefundService {

    // 환불 등록
    void requestRefund(MembershipRefundHistory refund);

    // 환불 목록
    List<RefundListDTO> getRefundListByKeyword(String searchType,
                                               String keyword,
                                               ActorRole actorRole,
                                               Long memberId);

    // 환불 상세
    RefundDetailDTO getRefundDetail(Long refundId, ActorRole actorRole);

}
