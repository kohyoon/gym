package com.project.gym.service;

import com.project.gym.common.PageResult;
import com.project.gym.domain.Admin;
import com.project.gym.dto.admin.AdminListDTO;
import com.project.gym.dto.admin.AdminPasswordDTO;
import com.project.gym.dto.admin.AdminSearchCriteria;
import com.project.gym.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void saveAdmin(Admin admin) {

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(admin.getAdminPassword());
        admin.setAdminPassword(encodedPassword);

        // DB 저장
        adminMapper.insertAdmin(admin);
    }

    @Override
    public boolean isUserIdDuplicate(String userId) {
        return adminMapper.countByUserId(userId) > 0;
    }

    @Override
    public PageResult<AdminListDTO> getAdminByCriteria(AdminSearchCriteria criteria) {
        int total = adminMapper.countAdminByCriteria(criteria);
        List<AdminListDTO> rows = (total > 0)
                ? adminMapper.selectAdminByCriteria(criteria)
                : java.util.Collections.emptyList();
        return new PageResult<>(rows, total, criteria.getPage(), criteria.getSize());
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

    @Override
    public void changePassword(AdminPasswordDTO passwordDTO) {

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(passwordDTO.getNewPassword());
        passwordDTO.setNewPassword(encodedPassword);

        adminMapper.updatePassword(passwordDTO);
    }
}
