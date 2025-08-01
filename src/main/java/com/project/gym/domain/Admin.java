package com.project.gym.domain;

import com.project.gym.domain.enums.AdminRole;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class Admin {
    private Long adminId;
    private String adminLoginId;
    private String adminPassword;

    private String confirmPassword;
    private String adminName;

    private AdminRole role;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;
}
