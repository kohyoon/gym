package com.project.gym.service;

import com.project.gym.common.PageResult;
import com.project.gym.domain.Member;
import com.project.gym.dto.member.*;

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

    // 회원 상세
    MemberDetailResponseDTO getMemberDetail(Long memberId);

    // 회원 정보 수정
    void updateMember(MemberUpdateFormDTO form);
    // 본인 제외 이메일 중복 확인
    boolean existsOtherUserByEmail(Long selfId, String email);
    // 회원 번호로 조회
    Member getById(Long memberId);

    void withdrawMember(Long memberId);

    // 비밀번호 변경
    void changeMemberPassword(MemberPasswordDTO dto);
}