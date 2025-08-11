package com.project.gym.mapper;

import com.project.gym.domain.Member;
import com.project.gym.dto.member.MemberDetailResponseDTO;
import com.project.gym.dto.member.MemberListDTO;
import com.project.gym.dto.member.MemberSearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    // 회원 상세
    MemberDetailResponseDTO selectMemberDetail(Long memberId);

    // 회원 정보 수정
    void updateMemberSelective(Member member);
    // 본인 제외 이메일 중복 확인
    boolean existsOtherByEmail(@Param("selfId") Long selfId, @Param("email") String email);
    // 회원 번호로 조회
    Member selectMemberById(Long memberId);

    // 로그인ID로 조회
    Member selectMemberByLoginId(String memberLoginId);

    // 회원 탈퇴
    void updateWithdrawStatus(Long memberId);
    
}
