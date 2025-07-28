package com.project.gym.mapper;

import com.project.gym.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {

    // 회원등록
    void insertMember(Member member);

    // 회원목록 전체 조회
    List<Member> findAllMembers();

    // 회원 번호로 조회
    Member findById(Long memberId);

    // 회원 정보 수정
    void updateMember(Member member);

    // 회원 정보 삭제
    void deleteById(Long memberId);

    // 회원 조회 by 이름
    List<Member> searchByTypeAndKeyword(@Param("searchType") String searchType,
                                        @Param("keyword") String keyword);


}
