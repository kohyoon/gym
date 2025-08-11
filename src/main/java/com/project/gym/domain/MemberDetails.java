package com.project.gym.domain;

import com.project.gym.domain.enums.MemberStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MemberDetails implements UserDetails {

    private final Member member;

    public MemberDetails(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return this.member;
    }

    public Long getMemberId() { return member.getMemberId(); }
    public String getMemberLoginId() { return member.getMemberLoginId(); }
    public MemberStatus getStatus() { return member.getStatus(); }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_MEMBER")); // Member
    }

    @Override
    public String getPassword() {
        return member.getMemberPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemberLoginId();  // 로그인 ID 기준
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return member.getStatus() != MemberStatus.WITHDRAW; // 3 = 탈퇴자면 로그인 제한
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return member.getStatus() == MemberStatus.NORMAL; // 1 = 정상
    }


}
