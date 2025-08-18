package com.project.gym.mapper;

import com.project.gym.domain.Membership;
import com.project.gym.dto.membership.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MembershipMapper {

    // 등록
    void insertMembership(MembershipCreateFormDTO dto);

    // 회원권 목록 조회
    List<MembershipListDTO> selectMembershipList(MembershipSearchCriteria criteria);
    int countMembershipList(MembershipSearchCriteria criteria);

    Membership findById(Long id);

    // 상세
    MembershipDetailDTO selectMembershipDetailById(Long membershipId);

    // 수정
    void updateMembership(Membership membership);
    MembershipUpdateFormDTO selectMembershipUpdateForm(Long membershipId);

    // 정지 상태 - EXTENDED_END_DATE 반영
    void updateStatusAndExtendedEndDate(Membership membership);


    // 환불
    void refundMembership(Long membershipId, Long updatedBy);

    // membershipStatus == 1(이용중) & 2(정지) 상태인 경우만 조회
    List<Membership> findAllActiveMemberships();

    // 종료처리
    void markMembershipsAsExpired(@Param("ids") List<Long> membershipIds);


}
