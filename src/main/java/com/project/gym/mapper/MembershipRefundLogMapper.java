package com.project.gym.mapper;

import com.project.gym.domain.MembershipRefundLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MembershipRefundLogMapper {
    // 환불 로그 추가
    void insertRefundLog(MembershipRefundLog log);

}
