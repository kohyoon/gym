package com.project.gym.mapper;

import com.project.gym.domain.Membership;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MembershipMapper {

    // 등록
    void insertMembership(Membership membership);

    // 목록
    List<Membership> selectAllMemberships();

    // 상세
    Membership selectMembershipByMembershipId(Long id);

    // 수정
    void updateMembership(Membership membership);

    // 삭제


}
