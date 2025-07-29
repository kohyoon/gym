package com.project.gym.service;

import com.project.gym.domain.Member;
import com.project.gym.domain.MemberDetails;
import com.project.gym.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements MemberDetailsService {

    private final MemberMapper memberMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberMapper.findByLoginId(username);
        if(member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 업습니다." + username);
        }

        return new MemberDetails(member);
    }
}
