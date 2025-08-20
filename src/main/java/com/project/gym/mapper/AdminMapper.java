package com.project.gym.mapper;

import com.project.gym.domain.Admin;
import com.project.gym.dto.admin.AdminListDTO;
import com.project.gym.dto.admin.AdminSearchCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    // 회원 추가 (가입)
    void insertAdmin(Admin admin);
    // 회원가입 중복체크
    int countByUserId(String userId);

    // USER_ID로 회원 검색
    Admin selectByUserId(String userId);
    // 관리자 조회
    List<AdminListDTO> selectAdminByCriteria(AdminSearchCriteria criteria);
    int countAdminByCriteria(AdminSearchCriteria criteria);

    // 회원정보 수정
    void updateAdmin(Admin admin);
    // 관리자번호(adminId)로 회원 정보 조회
    Admin selectByAdminId(Long adminId);

    // 회원 퇴사처리(상태변경)
    void updateRoleToResigned(Long adminId);

}
