package com.project.gym.mapper;

import com.project.gym.domain.MembershipSuspendHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MembershipSuspendMapper {

    // 정지 내역 추가
    void insertSuspendHistory(MembershipSuspendHistory suspendHistory);

    // 정지 내역 목록

    // 정지 내역 상세보기?

    // 정지 내역 수정


}
