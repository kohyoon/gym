package com.project.gym.service;

import com.project.gym.domain.Admin;
import com.project.gym.domain.Member;
import com.project.gym.domain.Membership;
import com.project.gym.domain.enums.MembershipStatus;
import com.project.gym.dto.membership.*;
import com.project.gym.mapper.AdminMapper;
import com.project.gym.mapper.MemberMapper;
import com.project.gym.mapper.MembershipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipMapper membershipMapper;
    private final MemberMapper memberMapper;
    private final AdminMapper adminMapper;

    @Override
    @Transactional
    public void registerMembership(MembershipCreateFormDTO dto) {
        membershipMapper.insertMembership(dto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembershipListDTO> findAllMemberships(MembershipSearchCriteria criteria) {
        normalize(criteria);

        int page = (criteria.getPage() == null || criteria.getPage() < 0) ? 0 : criteria.getPage();
        int size = (criteria.getSize() == null || criteria.getSize() < 0) ? 20 : Math.min(criteria.getSize(), 100);
        criteria.setPage(page);
        criteria.setSize(size);
        criteria.setOffset(page * size);

        return membershipMapper.selectMembershipList(criteria);
    }

    @Override
    public int countMemberships(MembershipSearchCriteria criteria) {
        normalize(criteria);

        int page = (criteria.getPage() == null || criteria.getPage() < 0) ? 0 : criteria.getPage();
        int size = (criteria.getSize() == null || criteria.getSize() < 0) ? 20 : Math.min(criteria.getSize(), 100);
        criteria.setPage(page);
        criteria.setSize(size);
        criteria.setOffset(page * size);

        return membershipMapper.countMembershipList(criteria);
    }

    private void normalize(MembershipSearchCriteria criteria) {
        // keyword 공백 제거
        if(criteria.getKeyword() != null) {
            String keyword = criteria.getKeyword().trim();
            criteria.setKeyword(keyword.isEmpty() ? null : keyword);
        }
    }

    @Override
    public MembershipDetailDTO getMembershipDetail(Long membershipId) {
        return membershipMapper.selectMembershipDetailById(membershipId);
    }

    @Override
    public Membership findById(Long id) {
        return membershipMapper.findById(id);
    }

    @Override
    public void updateMembership(MembershipUpdateFormDTO dto) {
        if(dto == null || dto.getMembershipId() == null) {
            throw new IllegalArgumentException("회원권 정보가 올바르지 않습니다.");
        }

        if(dto.getMembershipStatus() == MembershipStatus.REFUND) {
            // 환불 관련 처리
        }

        Membership entity = toMembershipEntity(dto);
        membershipMapper.updateMembership(entity);
    }

    @Override
    public MembershipUpdateFormDTO getMembershipEditForm(Long membershipId, Long updatedBy) {
        MembershipUpdateFormDTO dto = membershipMapper.selectMembershipUpdateForm(membershipId);
        dto.setUpdatedBy(updatedBy);
        return dto;
    }

    // Domain -> DTO
    public MembershipUpdateFormDTO toUpdateFormDTO(Membership m, Member mem, Admin createdAdmin,
                                                   Admin updatedAdmin) {
        MembershipUpdateFormDTO dto = new MembershipUpdateFormDTO();
        // 회원 정보
        dto.setMemberId(mem.getMemberId());
        dto.setMemberName(mem.getMemberName());
        dto.setMemberLoginId(mem.getMemberLoginId());
        dto.setEmail(mem.getEmail());
        dto.setPhone(mem.getPhone());

        // 회원권 정보
        dto.setMembershipId(m.getMembershipId());
        dto.setMembershipType(m.getMembershipType());
        dto.setPeriodDays(m.getPeriodDays());
        dto.setStartDate(m.getStartDate());
        dto.setEndDate(m.getEndDate());
        dto.setPrice(m.getPrice());
        dto.setMembershipStatus(m.getMembershipStatus());
        dto.setPaymentMethod(m.getPaymentMethod());

        // 생성자, 수정자
        dto.setCreatedBy(m.getCreatedBy());
        dto.setCreatedByName(createdAdmin != null ? createdAdmin.getAdminName() : null);
        dto.setUpdatedBy(m.getUpdatedBy());

        // 정지 연장일
        dto.setExtendedEndDate(m.getExtendedEndDate());

        return dto;
    }

    // DTO -> Domain
    public Membership toMembershipEntity(MembershipUpdateFormDTO dto) {
        Membership m = new Membership();

        m.setMembershipId(dto.getMembershipId());
        m.setMemberId(dto.getMemberId());
        m.setMembershipType(dto.getMembershipType());
        m.setPeriodDays(dto.getPeriodDays());
        m.setStartDate(dto.getStartDate());
        m.setEndDate(dto.getEndDate());
        m.setPrice(dto.getPrice());
        m.setMembershipStatus(dto.getMembershipStatus());
        m.setPaymentMethod(dto.getPaymentMethod());
        m.setUpdatedBy(dto.getUpdatedBy());

        // 정지 연장일
        m.setExtendedEndDate(dto.getExtendedEndDate());

        return m;
    }


    // 내부 전용 정지 처리 메서드
    private void handleSuspend(Membership membership) {
//        LocalDate today = LocalDate.now();
//        if (membership.getStartDate().isAfter(today)) {
//            throw new IllegalStateException("시작되지 않은 회원권은 정지할 수 없습니다. 시작일을 변경해주세요.");
//        }
//
//        long remaining = ChronoUnit.DAYS.between(today, membership.getEndDate());
//        if (remaining < 7) {
//            throw new IllegalStateException("남은 이용일이 7일 미만인 회원권은 정지할 수 없습니다.");
//        }
//        membershipMapper.insertSuspendHistory(history);
    }

    @Override
    @Transactional
    public void markExpiredMemberships() {
//        LocalDate today = LocalDate.now();
//
//        List<Membership> activeMemberships = membershipMapper.findAllActiveMemberships();
//
//        List<Long> expiredIds = activeMemberships.stream()
//                .filter(m -> {
//                    LocalDate endDate = (m.getExtendedEndDate() != null) ?
//                            m.getExtendedEndDate() : m.getEndDate();
//                    return endDate.isBefore(today);
//                })
//                .map(Membership::getMembershipId)
//                .collect(Collectors.toList());
//
//        if (!expiredIds.isEmpty()) {
//            membershipMapper.markMembershipsAsExpired(expiredIds);
//        }
    }

    @Override
    public List<Membership> getActiveMemberships() {
        return membershipMapper.findAllActiveMemberships();
    }

    @Override
    public void updateStatusAndExtendedEndDate(Membership membership) {
        membershipMapper.updateStatusAndExtendedEndDate(membership);
    }



}