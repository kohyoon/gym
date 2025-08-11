package com.project.gym.mapper;

import com.project.gym.domain.Member;
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

    // 회원목록 전체 조회
    List<Member> findAllMembers();

    // 회원 번호로 조회
    Member findById(Long memberId);
    // 로그인 아이디로 조회
    Member findByLoginId(String memberLoginId);

    // 회원 정보 수정
    void updateMember(Member member);

    // 회원 정보 삭제
    void updateWithdrawStatus(Long memberId);

    // 회원 조회 by 이름
    List<Member> searchByTypeAndKeyword(@Param("searchType") String searchType,
                                        @Param("keyword") String keyword);


}
