package com.project.gym.mapper;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.dto.membership.refund.RefundDetailDTO;
import com.project.gym.dto.membership.refund.RefundListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MembershipRefundMapper {
    // 환불 등록
    void insertRefund(MembershipRefundHistory refund);

    // 환불 목록 (검색)
    List<RefundListDTO> selectRefundListByKeyword(@Param("searchType") String searchType,
                                                  @Param("keyword") String keyword,
                                                  @Param("actorRole") String actorRole,
                                                  @Param("memberId") Long memberId);

    // 환불 상세
    RefundDetailDTO selectRefundDetailById(Long refundId);

}
