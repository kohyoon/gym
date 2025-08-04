package com.project.gym.mapper;

import com.project.gym.domain.Membership;
import com.project.gym.domain.MembershipSuspendHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MembershipMapper {

    // 등록
    void insertMembership(Membership membership);

    // 목록
    List<Membership> findAllMemberships();
    List<Membership> findMembershipByMemberId(Long memberId);

    // 상세
    Membership findById(Long id);

    // 수정
    void updateMembership(Membership membership);

    // 환불

    // 정지 상태 - EXTENDED_END_DATE 반영
    void updateStatusAndExtendedEndDate(Membership membership);


    // membershipStatus == 1(이용중) & 2(정지) 상태인 경우만 조회
    List<Membership> findAllActiveMemberships();

    // 종료처리
    void markMembershipsAsExpired(@Param("ids") List<Long> membershipIds);


}
