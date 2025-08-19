package com.project.gym.service;

import com.project.gym.domain.MembershipSuspendHistory;
import com.project.gym.dto.membership.suspend.SuspendCreateFormDTO;
import com.project.gym.dto.membership.suspend.SuspendDetailDTO;
import com.project.gym.dto.membership.suspend.SuspendListDTO;
import com.project.gym.dto.membership.suspend.SuspendSearchCriteria;

import java.util.List;

public interface MembershipSuspendService {

    // 정지 내역 추가
    void insertSuspendHistory(SuspendCreateFormDTO dto);

    // 정지 내역 전체
    List<MembershipSuspendHistory> getAllSuspendHistories();

    // 정지 내역 조회 - by MembershipId
    List<MembershipSuspendHistory> getSuspendHistoriesByMembershipId(Long membershipId);
    List<SuspendListDTO> getSuspendDTOByMembershipId(Long membershipId);

    List<MembershipSuspendHistory> getSuspendHistoriesByMemberId(Long memberId);

    // 정지 내역 조회 - criteria
    List<SuspendListDTO> searchSuspendMemberships(SuspendSearchCriteria criteria);

//    MembershipSuspendHistory getSuspendHistoryById(Long suspendId);

    // 정지 상세 내역
    SuspendDetailDTO getSuspendDetailById(Long suspendId);

}
