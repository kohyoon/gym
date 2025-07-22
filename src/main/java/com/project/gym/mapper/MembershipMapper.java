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
    List<Membership> selectAllMemberships();

    // 상세
    Membership selectMembershipByMembershipId(Long id);

    // 수정
    void updateMembership(Membership membership);

    // 환불

    // 종료

    //====== 정지 ======//
    void insertSuspendHistory(MembershipSuspendHistory history);

    // membershipStatus == 1(이용중) & 2(정지) 상태인 경우만 조회
    List<Membership> findAllActiveMemberships();

    // 종료처리
    void updateMembershipsToExpired(@Param("ids") List<Long> membershipIds);


}
