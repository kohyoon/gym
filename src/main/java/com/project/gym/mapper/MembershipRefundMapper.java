package com.project.gym.mapper;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.MembershipRefundLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MembershipRefundMapper {

    // 환불 신청
    void insertRefundRequestHistory(MembershipRefundHistory refundHistory);
    void insertRefundRequestLog(MembershipRefundLog refundLog);

}
