package com.project.gym.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Membership {

    private Long membershipId;
    private Long memberId;
    @NotBlank(message = "회원권 종류를 선택해주세요")
    private String membershipType;
    @NotNull(message = "이용 기간을 선택해주세요")
    private Integer periodDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
    private Integer membershipStatus; // 1이용중 2종료 3환불
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
