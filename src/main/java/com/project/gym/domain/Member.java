package com.project.gym.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;

@Data
public class Member {

    private Long memberId;
    private String memberLoginId;
    private String memberPassword;

    private String memberName;

    private String phone;

    private String email;

    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")  // 폼에서 넘어온 날짜 문자열을 LocalDate로 변환
    private LocalDate birthDate;

    private Integer status; // 1 정상 2 중지 3 탈퇴

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;  // INSERT 시 Oracle에서 SYSDATE로 자동 입력
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;  // INSERT 시 Oracle에서 SYSDATE로 자동 입력

}
