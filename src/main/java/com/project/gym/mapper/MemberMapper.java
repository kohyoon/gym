package com.project.gym.mapper;

import com.project.gym.domain.Member;
import com.project.gym.dto.member.MemberListDTO;
import com.project.gym.dto.member.MemberSearchCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    // 회원등록
    void insertMember(Member member);
    // 아이디 중복 확인
    boolean existsByLoginId(String memberLoginId);
    // 이메일 중복 확인
    boolean existsByEmail(String email);

    // 회원목록 조회
    List<MemberListDTO> selectMemberList(MemberSearchCondition searchCondition);
    int countMembers(MemberSearchCondition condition);

    // 회원 번호로 조회
    Member findById(Long memberId);
    // 로그인 아이디로 조회
    Member findByLoginId(String memberLoginId);

    // 회원 정보 수정
    void updateMember(Member member);

    // 회원 탈퇴
    void updateWithdrawStatus(Long memberId);
    
}
