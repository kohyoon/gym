package com.project.gym.service;

import com.project.gym.domain.Membership;
import com.project.gym.domain.MembershipSuspendHistory;
import com.project.gym.domain.enums.MembershipStatus;
import com.project.gym.mapper.MembershipMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipMapper membershipMapper;

    public MembershipServiceImpl(MembershipMapper membershipMapper) {
        this.membershipMapper = membershipMapper;
    }

    @Override
    @Transactional
    public void registerMembership(Membership membership) {
        membershipMapper.insertMembership(membership);
    }

    @Override
    public List<Membership> findAllMemberships() {
        return membershipMapper.findAllMemberships();
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