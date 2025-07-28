package com.project.gym.service;

import com.project.gym.domain.Admin;
import com.project.gym.mapper.AdminMapper;
import com.project.gym.mapper.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void saveAdmin(Admin admin) {

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);

        // DB 저장
        adminMapper.insertAdmin(admin);
    }

    @Override
    public boolean isUserIdDuplicate(String userId) {
        return adminMapper.countByUserId(userId) > 0;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminMapper.selectAllAdmins();
    }

    @Override
    public void updateAdmin(Admin admin) {
        adminMapper.updateAdmin(admin);
    }

    @Override
    public Admin getAdminById(Long adminId) {
        return adminMapper.selectByAdminId(adminId);
    }

    @Override
    public void resignAdmin(Long adminId) {
        adminMapper.updateRoleToResigned(adminId);
    }
}
