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
    public void registerMember(Member member) {
        memberMapper.insertMember(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberMapper.findAllMembers();
    }

    @Override
    public Member getMemberById(Long id) {
        return memberMapper.findById(id);
    }

    @Override
    public void updateMember(Member member) {
        memberMapper.updateMember(member);
    }

    @Override
    public void deleteMember(Long id) {
        memberMapper.deleteById(id);
    }

    @Override
    public List<Member> searchMembersByKeyword(String searchType, String keyword) {
        return memberMapper.searchByTypeAndKeyword(searchType, keyword);
    }
}