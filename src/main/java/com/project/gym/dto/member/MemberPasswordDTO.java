package com.project.gym.dto.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberPasswordDTO {

    private Long memberId;
    private Long memberLoginId;
    private String memberName;

    private String currentPassword;
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8~16자여야 합니다.")
    private String newPassword;
    @NotBlank(message = "비밀번호 확인은 필수 입력입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8~16자여야 합니다.")
    private String confirmPassword;

    private LocalDate updatedAt;

}
