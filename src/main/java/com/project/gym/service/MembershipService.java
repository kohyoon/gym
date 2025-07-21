package com.project.gym.service;

import com.project.gym.domain.Membership;
import com.project.gym.mapper.MembershipMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public void updateMembership(Membership membership) {
        membershipMapper.updateMembership(membership);
    }




}
