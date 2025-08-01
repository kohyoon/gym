package com.project.gym.mapper;

import com.project.gym.domain.MembershipSuspendHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MembershipSuspendMapper {

    // 정지 내역 추가
    void insertSuspendHistory(MembershipSuspendHistory suspendHistory);

    // 정지 내역 - 전체 목록
    List<MembershipSuspendHistory> selectAllSuspendHistories();

    // 정지 내역 상세보기?

    // 정지 내역 수정


}
