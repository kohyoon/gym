package com.project.gym.service;

import com.project.gym.common.PageResult;
import com.project.gym.domain.Member;
import com.project.gym.dto.member.MemberCreateFormDTO;
import com.project.gym.dto.member.MemberListDTO;
import com.project.gym.dto.member.MemberSearchCondition;

import java.util.List;

public interface MemberService {
    // 회원 가입
    void registerMember(MemberCreateFormDTO form);
    // 로그인 아이디 중복 확인
    boolean existsByLoginId(String memberLoginId);
    // 이메일 중복 확인
    boolean existsByEmail(String email);

    // 회원 목록 조회
    PageResult<MemberListDTO> getMemberPage(MemberSearchCondition condition);

    Member getMemberById(Long id);
    void updateMember(Member member);
    void withdrawMember(Long memberId);

}