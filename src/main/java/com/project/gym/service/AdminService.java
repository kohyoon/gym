package com.project.gym.service;

import com.project.gym.domain.Admin;

import java.util.List;

public interface AdminService {

    void saveAdmin(Admin admin);
    boolean isUserIdDuplicate(String userId);

    List<Admin> getAllAdmins();

    void updateAdmin(Admin admin);
    Admin getAdminById(Long adminId);

    void resignAdmin(Long adminId);

}
