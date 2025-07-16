package com.project.gym.service;

import com.project.gym.domain.Member;
import com.project.gym.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public MemberService (MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Transactional
    public void insertMember(Member member) {
        memberMapper.insertMember(member);
    }

    public List<Member> getAllMembers() {
        return memberMapper.selectAllMembers();
    }

    public Member getMemberById(Long id) {
        return memberMapper.selectMemberById(id);
    }

    public void updateMember(Member member) {
        memberMapper.updateMember(member);
    }

    public void deleteMember(Long id){
        memberMapper.deleteMember(id);
    }

}
