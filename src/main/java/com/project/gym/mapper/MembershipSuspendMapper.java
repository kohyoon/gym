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

    // 정지 내역 - MEMBERSHIP_ID 로 조회
    List<MembershipSuspendHistory> selectSuspendHistoriesByMembershipId(Long membershipId);

    // 정지 내역 - MEMBER_ID 로 조회
    List<MembershipSuspendHistory> selectSuspendHistoriesByMemberId(Long memberId);

    // 정지 내역 상세보기
    MembershipSuspendHistory selectSuspendHistoryById(Long suspendId);

    // 정지 내역 수정


}
