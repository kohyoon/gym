package com.project.gym.service;

import com.project.gym.domain.Membership;
import com.project.gym.domain.MembershipSuspendHistory;
import com.project.gym.mapper.MembershipMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MembershipService {

    private final MembershipMapper membershipMapper;

    public MembershipService (MembershipMapper membershipMapper) {
        this.membershipMapper = membershipMapper;
    }

    @Transactional
    public void insertMembership(Membership membership) {
        membershipMapper.insertMembership(membership);
    }

    public List<Membership> getAllMemberships() {
        return membershipMapper.selectAllMemberships();
    }

    public Membership findByMembershipId(Long id) {
        return membershipMapper.selectMembershipByMembershipId(id);
    }

    // 수정
    public void updateMembership(Membership membership) {

        //===== 정지상태 (membershipStatus == 2) 시작 =====//
        if(membership.getMembershipStatus() == 2) {

            LocalDate today = LocalDate.now();
            if(membership.getStartDate().isAfter(today)) {
                throw new IllegalStateException("시작되지 않은 회원권은 정지할 수 없습니다. 시작일을 변경해주세요.");
            }

            // 남은 기간 계산
            long remaining = ChronoUnit.DAYS.between(today, membership.getEndDate());
            if(remaining < 7) {
                throw new IllegalStateException("남은 이용일이 7일 미만인 회원권은 정지할 수 없습니다.");
            }
            
            LocalDate extendedEnd = membership.getSuspendEndDate().plusDays(remaining);
            membership.setRemainingDays((int) remaining);
            membership.setExtendedEndDate(extendedEnd);

            // 정지 이력 저장
            MembershipSuspendHistory history = new MembershipSuspendHistory();
            history.setMembershipId(membership.getMembershipId());
            history.setSuspendStartDate(membership.getSuspendStartDate());
            history.setSuspendEndDate(membership.getSuspendEndDate());
            history.setRecordedBy("admin"); // 관리자 등록을 따로 해야됨 -> 아직 안해서 임시로 admin이라고 표시
            membershipMapper.insertSuspendHistory(history);
        }
        //===== 정지상태 (membershipStatus == 2) 끝 =====//


        
        // 환불

        membershipMapper.updateMembership(membership);
    }

    //===== 종료상태 (membershipStatus == 4) 자동 반영 =====//
    @Transactional
    public void updateExpiredMemberships() {
        LocalDate today = LocalDate.now();

        // 종료일이 지난 회원권 목록 조회
        List<Membership> activeMemberships = membershipMapper.findAllActiveMemberships();

        List<Long> expiredIds = activeMemberships.stream()
                .filter(m -> {
                    LocalDate endDate = (m.getExtendedEndDate() != null) ?
                            m.getExtendedEndDate()
                            : m.getEndDate();
                    return endDate.isBefore(today);
                })
                .map(Membership::getMembershipId)
                .collect(Collectors.toList());

        if(!expiredIds.isEmpty()) {
            membershipMapper.updateMembershipsToExpired(expiredIds);
        }


    }

    // membershipStatus == 1(이용중) & 2(정지) 상태인 경우만 조회
    public List<Membership> getActiveMemberships() {
        return membershipMapper.findAllActiveMemberships();
    }





}
