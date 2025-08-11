package com.project.gym.config;

import com.project.gym.service.AdminDetailsService;
import com.project.gym.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AdminDetailsService adminDetailsService;
    private final MemberDetailsService memberDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 관리자용 SecurityFilterChain
    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/admin/**", "/auth/admin_login", "/auth/logout")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/admin_login", "/admin/signup").permitAll()
                        .anyRequest().hasAnyRole("ADMIN")
                )
                .formLogin(form -> form
                        .loginPage("/auth/admin_login")
                        .loginProcessingUrl("/auth/admin_login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/admin/adminPage", true) // 로그인 성공 시 이동 경로
                        .failureUrl("/auth/admin_login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout") // 사용자가 요청할 로그아웃 경로
                        .logoutSuccessUrl("/auth/admin_login?logout") // 로그아웃 후 리디렉션할 경로
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll()
                )
                .userDetailsService(adminDetailsService)
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    // 회원용 SecurityFilterChain
    @Bean
    @Order(2)
    public SecurityFilterChain memberSecurityFilterChain(HttpSecurity http) throws Exception {

        http.
                securityMatcher("/member/**", "/auth/member_login", "/auth/logout", "/membership/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/member/check-loginId", "/member/check-email", "/auth/member_login", "/member/signup").permitAll()
                        .anyRequest().hasAnyRole("MEMBER", "ADMIN")
                )
                .formLogin(form -> form
                        .loginPage("/auth/member_login")
                        .loginProcessingUrl("/auth/member_login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/member/mypage", true)
                        .failureUrl("/auth/member_login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/member_login?logout")
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll()
                )
                .userDetailsService(memberDetailsService)
                .csrf(csrf -> csrf.disable());

        return http.build();



    }

}
