package com.project.gym.service;

import com.project.gym.domain.Member;
import com.project.gym.mapper.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void registerMember(Member member) {

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(member.getMemberPassword());
        member.setMemberPassword(encodedPassword);

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
    public void withdrawMember(Long memberId) {
        memberMapper.updateWithdrawStatus(memberId);
    }

    @Override
    public List<Member> searchMembersByKeyword(String searchType, String keyword) {
        return memberMapper.searchByTypeAndKeyword(searchType, keyword);
    }
}