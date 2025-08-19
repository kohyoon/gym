package com.project.gym.mapper;

import com.project.gym.domain.MembershipRefundLog;
import com.project.gym.dto.refund.RefundLogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MembershipRefundLogMapper {
    // 환불 로그 추가
    void insertRefundLog(MembershipRefundLog log);

    // 로그 조회 - refundId
    List<RefundLogDTO> selectLogsByRefundId(@Param("refundId") Long refundId,
                                            @Param("actorRole") String actorRole);


}
