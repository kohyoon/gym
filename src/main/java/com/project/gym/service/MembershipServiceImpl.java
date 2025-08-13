package com.project.gym.service;

import com.project.gym.domain.Membership;
import com.project.gym.domain.enums.MembershipStatus;
import com.project.gym.dto.membership.MembershipCreateFormDTO;
import com.project.gym.dto.membership.MembershipListDTO;
import com.project.gym.dto.membership.MembershipSearchCriteria;
import com.project.gym.mapper.MembershipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipMapper membershipMapper;

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
    public List<Membership> getMembershipsByMemberId(Long memberId) {
        return membershipMapper.findMembershipByMemberId(memberId);
    }

    @Override
    public Membership findById(Long id) {
        return membershipMapper.findById(id);
    }

    @Override
    public void updateMembership(Membership membership) {
        MembershipStatus status = membership.getMembershipStatus();
        if (status == MembershipStatus.FINISHED) {
            handleSuspend(membership);
        } else if (status == MembershipStatus.REFUND) {
            // handleRefund(membership); // 추후 구현 예정
        }

        membershipMapper.updateMembership(membership);
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