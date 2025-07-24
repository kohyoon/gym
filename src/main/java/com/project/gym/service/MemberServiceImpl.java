package com.project.gym.service;

import com.project.gym.domain.Member;
import com.project.gym.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    @Transactional
    public void insertMember(Member member) {
        memberMapper.insertMember(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberMapper.selectAllMembers();
    }

    @Override
    public Member getMemberById(Long id) {
        return memberMapper.selectMemberById(id);
    }

    @Override
    public void updateMember(Member member) {
        memberMapper.updateMember(member);
    }

    @Override
    public void deleteMember(Long id) {
        memberMapper.deleteMember(id);
    }

    @Override
    public List<Member> searchMembers(String searchType, String keyword) {
        return memberMapper.searchMembers(searchType, keyword);
    }
}