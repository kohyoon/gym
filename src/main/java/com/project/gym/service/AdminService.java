package com.project.gym.service;

import com.project.gym.domain.Admin;

import java.util.List;

public interface AdminService {

    void saveAdmin(Admin admin);
    boolean isUserIdDuplicate(String userId);

    List<Admin> getAllAdmins();

}
