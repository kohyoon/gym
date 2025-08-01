package com.project.gym.service;

import com.project.gym.domain.MembershipSuspendHistory;
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
    public void addSuspendHistory(MembershipSuspendHistory suspendHistory) {
        suspendMapper.insertSuspendHistory(suspendHistory);
    }

    @Override
    public List<MembershipSuspendHistory> getAllSuspendHistories() {
        return suspendMapper.selectAllSuspendHistories();
    }


}
