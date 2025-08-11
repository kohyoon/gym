package com.project.gym.dto.member;

import com.project.gym.domain.enums.Gender;
import com.project.gym.domain.enums.MemberStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberListDTO {
    private Long memberId;
    private String memberLoginId;
    private String memberName;
    private String phone;
    private String email;
    private Gender gender;
    private MemberStatus status;
    private LocalDateTime createdAt;
}
