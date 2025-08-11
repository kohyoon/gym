package com.project.gym.domain;

import com.project.gym.domain.enums.Gender;
import com.project.gym.domain.enums.MemberStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Member {

    private Long memberId;
    private String memberLoginId;
    private String memberPassword;

    private String memberName;

    private String phone;

    private String email;

    private Gender gender; // M, F

    @DateTimeFormat(pattern = "yyyy-MM-dd")  // 폼에서 넘어온 날짜 문자열을 LocalDate로 변환
    private LocalDate birthDate;

    private MemberStatus status; // NORMAL, WITHDRAW

    private LocalDateTime createdAt; // DEFAULT SYSDATE
    private LocalDateTime updatedAt; // null or SYSDATE on update

}
