package com.project.gym.service;

import com.project.gym.domain.Member;
import java.util.List;

public interface MemberService {
    void registerMember(Member member);
    List<Member> getAllMembers();
    Member getMemberById(Long id);
    void updateMember(Member member);
    void withdrawMember(Long memberId);
    List<Member> searchMembersByKeyword(String searchType, String keyword);
}