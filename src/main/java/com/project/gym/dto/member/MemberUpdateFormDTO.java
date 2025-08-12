package com.project.gym.dto.member;

import com.project.gym.domain.enums.Gender;
import com.project.gym.domain.enums.MemberStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MemberUpdateFormDTO {
    private Long memberId; // hidden
    private String memberLoginId;
    private String memberPassword;
    private String memberName;

    @NotBlank(message = "전화번호는 필수 입력입니다.")
    // 하이픈 없이 입력해도 허용 → 저장 시 포맷 보정
    @Pattern(regexp = "^(010-?\\d{4}-?\\d{4})$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phone;

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    private Gender gender;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birthDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 비밀번호 변경
//    @Size(min = 8, max = 16, message = "비밀번호는 8~16자여야 합니다.")
//    private String newPassword;
    private String currentPassword;
}
