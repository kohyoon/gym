package com.project.gym.domain;

import com.project.gym.domain.enums.Role;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class Admin {
    private Integer adminId;
    private String userId;
    private String password;

    private String confirmPassword;
    private String adminName;

    private Role role;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
}
