package com.project.gym.service;

import com.project.gym.domain.Admin;
import com.project.gym.domain.AdminDetails;
import com.project.gym.domain.enums.AdminRole;
import com.project.gym.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDetailsServiceImpl implements AdminDetailsService {

    private final AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Admin admin = adminMapper.selectByUserId(userId);
        if(admin == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 업습니다." + userId);
        }
        if(admin.getRole() == AdminRole.RESIGNED) {
            throw new UsernameNotFoundException("퇴사한 회원입니다.");
        }

        return new AdminDetails(admin);
    }
}
