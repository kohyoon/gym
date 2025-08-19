package com.project.gym.mapper;

import com.project.gym.domain.MembershipSuspendHistory;
import com.project.gym.dto.suspend.SuspendCreateFormDTO;
import com.project.gym.dto.suspend.SuspendDetailDTO;
import com.project.gym.dto.suspend.SuspendListDTO;
import com.project.gym.dto.suspend.SuspendSearchCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MembershipSuspendMapper {

    // 정지 내역 추가
    void insertSuspendHistory(SuspendCreateFormDTO dto);

    // 정지 내역 - 전체 목록
    List<MembershipSuspendHistory> selectAllSuspendHistories();

    // 정지 내역 - MEMBERSHIP_ID 로 조회
    List<MembershipSuspendHistory> selectSuspendHistoriesByMembershipId(Long membershipId);
    List<SuspendListDTO> selectSuspendListDTOByMembershipId(Long membershipId);

    // 정지 내역 조회
    List<SuspendListDTO> selectSuspendListDTOByCriteria(@Param("criteria") SuspendSearchCriteria criteria);


    // 정지 내역 - MEMBER_ID 로 조회
    List<MembershipSuspendHistory> selectSuspendHistoriesByMemberId(Long memberId);

    // 정지 내역 상세보기
    SuspendDetailDTO selectSuspendDetailById(Long suspendId);
    // 정지 내역 수정


}
