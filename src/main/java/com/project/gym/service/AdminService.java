package com.project.gym.service;

import com.project.gym.common.PageResult;
import com.project.gym.domain.Admin;
import com.project.gym.dto.admin.AdminListDTO;
import com.project.gym.dto.admin.AdminSearchCriteria;

public interface AdminService {

    void saveAdmin(Admin admin);
    boolean isUserIdDuplicate(String userId);

    // 관리자 목록 조회
    PageResult<AdminListDTO> getAdminByCriteria(AdminSearchCriteria criteria);


    void updateAdmin(Admin admin);
    Admin getAdminById(Long adminId);

    void resignAdmin(Long adminId);

}
