package com.project.gym.mapper;

import com.project.gym.domain.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    // 회원 추가 (가입)
    void insertAdmin(Admin admin);
    // 회원가입 중복체크
    int countByUserId(String userId);

    // 회원 목록
    List<Admin> selectAllAdmins();
    // USER_ID로 회원 검색
    Admin selectByUserId(String userId);

}
