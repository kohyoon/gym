package com.project.gym.mapper;

import com.project.gym.domain.MembershipRefundHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MembershipRefundMapper {
    // 환불 등록
    void insertRefund(MembershipRefundHistory refund);
}
