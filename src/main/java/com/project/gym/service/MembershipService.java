package com.project.gym.service;

import com.project.gym.domain.Membership;
import com.project.gym.dto.membership.MembershipCreateFormDTO;
import com.project.gym.dto.membership.MembershipDetailDTO;
import com.project.gym.dto.membership.MembershipListDTO;
import com.project.gym.dto.membership.MembershipSearchCriteria;

import java.util.List;

public interface MembershipService {

    // 회원권 등록
    void registerMembership(MembershipCreateFormDTO dto);

    // 회원권 목록 조회
    List<MembershipListDTO> findAllMemberships(MembershipSearchCriteria criteria);
    int countMemberships(MembershipSearchCriteria criteria);

    // 회원권 상세
    MembershipDetailDTO getMembershipDetail(Long membershipId);

    Membership findById(Long id);

    void updateMembership(Membership membership);

    void markExpiredMemberships();

    List<Membership> getActiveMemberships();

    void updateStatusAndExtendedEndDate(Membership membership);
}