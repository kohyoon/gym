package com.project.gym.dto.member;

import com.project.gym.domain.enums.Gender;
import com.project.gym.domain.enums.MemberStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MemberDetailResponseDTO {
    private Long memberId;
    private String memberLoginId;
    private String memberName;
    private String phone;
    private String email;
    private Gender gender;
    private LocalDate birthDate;
    private MemberStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
