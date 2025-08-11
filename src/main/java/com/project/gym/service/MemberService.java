package com.project.gym.service;

import com.project.gym.domain.Member;
import com.project.gym.dto.member.MemberCreateFormDTO;

import java.util.List;

public interface MemberService {
    // 회원 가입
    void registerMember(MemberCreateFormDTO form);
    // 로그인 아이디 중복 확인
    boolean existsByLoginId(String memberLoginId);
    // 이메일 중복 확인
    boolean existsByEmail(String email);

    List<Member> getAllMembers();
    Member getMemberById(Long id);
    void updateMember(Member member);
    void withdrawMember(Long memberId);
    List<Member> searchMembersByKeyword(String searchType, String keyword);
}