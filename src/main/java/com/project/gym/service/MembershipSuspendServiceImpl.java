package com.project.gym.service;

import com.project.gym.domain.MembershipSuspendHistory;
import com.project.gym.dto.membership.suspend.SuspendCreateFormDTO;
import com.project.gym.dto.membership.suspend.SuspendDetailDTO;
import com.project.gym.dto.membership.suspend.SuspendListDTO;
import com.project.gym.mapper.MembershipSuspendMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MembershipSuspendServiceImpl implements MembershipSuspendService{

    private final MembershipSuspendMapper suspendMapper;

    public MembershipSuspendServiceImpl (MembershipSuspendMapper suspendMapper) {
        this.suspendMapper = suspendMapper;
    }

    @Override
    @Transactional
    public void insertSuspendHistory(SuspendCreateFormDTO dto) {
        suspendMapper.insertSuspendHistory(dto);
    }

    @Override
    public List<MembershipSuspendHistory> getAllSuspendHistories() {
        return suspendMapper.selectAllSuspendHistories();
    }

    @Override
    public List<MembershipSuspendHistory> getSuspendHistoriesByMembershipId(Long membershipId) {
        return suspendMapper.selectSuspendHistoriesByMembershipId(membershipId);
    }

    @Override
    public List<SuspendListDTO> getSuspendDTOByMembershipId(Long membershipId) {
        return suspendMapper.selectSuspendListDTOByMembershipId(membershipId);
    }

    @Override
    public List<MembershipSuspendHistory> getSuspendHistoriesByMemberId(Long memberId) {
        return suspendMapper.selectSuspendHistoriesByMemberId(memberId);
    }

//    @Override
//    public MembershipSuspendHistory getSuspendHistoryById(Long suspendId) {
//        return suspendMapper.selectSuspendHistoryById(suspendId);
//    }

    @Override
    public SuspendDetailDTO getSuspendDetailById(Long suspendId) {
        return suspendMapper.selectSuspendDetailById(suspendId);
    }


}
