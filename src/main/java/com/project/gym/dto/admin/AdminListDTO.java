package com.project.gym.dto.admin;

import com.project.gym.domain.enums.AdminRole;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminListDTO {

    private Long adminId;

    private String adminLoginId;
    private String adminName;

    private String adminEmail;
    private String adminPhone;

    private AdminRole role;

    private LocalDate createdAt;

}
