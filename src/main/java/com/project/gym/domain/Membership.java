package com.project.gym.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class Membership {

    private Long membershipId;
    private Long memberId;
    @NotBlank(message = "회원권 종류를 선택해주세요")
    private String membershipType;
    @NotNull(message = "이용 기간을 선택해주세요")
    private Integer periodDays;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private int price;
    private Integer membershipStatus; // 1이용중 2종료 3환불
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private String memberName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate suspendStartDate; // 정지시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate suspendEndDate; // 정지종료일
    private Integer remainingDays; // 남은 기간
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedEndDate; // 정지일 반영 새로운 종료일

}
