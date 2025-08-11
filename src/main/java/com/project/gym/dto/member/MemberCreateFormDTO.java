package com.project.gym.dto.member;

import com.project.gym.domain.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MemberCreateFormDTO {

    @NotBlank(message = "아이디는 필수 입력입니다.")
    @Size(min = 4, max = 20, message = "아이디는 4~20자여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문, 숫자만 가능합니다.")
    private String memberLoginId;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8~16자여야 합니다.")
    private String memberPassword;

    @NotBlank(message = "비밀번호 확인은 필수 입력입니다.")
    private String confirmPassword;

    @NotBlank(message = "이름은 필수 입력입니다.")
    private String memberName;

    @NotBlank(message = "전화번호는 필수 입력입니다.")
    //@Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    @Pattern(regexp = "^(010-?\\d{4}-?\\d{4})$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phone;

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotNull(message = "성별을 선택해주세요.")
    private Gender gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birthDate;

}
