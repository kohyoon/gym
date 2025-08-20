package com.project.gym.domain;

import com.project.gym.domain.enums.AdminRole;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Admin {
    private Long adminId;

    @NotBlank(message = "아이디는 필수 입력입니다.")
    @Size(min = 4, max = 20, message = "아이디는 4~20자여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문, 숫자만 가능합니다.")
    private String adminLoginId;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8~16자여야 합니다.")
    private String adminPassword;

    @NotBlank(message = "비밀번호 확인은 필수 입력입니다.")
    private String confirmPassword;

    @NotBlank(message = "이름은 필수 입력입니다.")
    private String adminName;

    @NotNull(message = "직급을 선택해주세요.")
    private AdminRole role;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String adminEmail;

    @NotBlank(message = "전화번호는 필수 입력입니다.")
    @Pattern(regexp = "^(010-?\\d{4}-?\\d{4})$", message = "전화번호 형식이 올바르지 않습니다.")
    private String adminPhone;
}
