package com.project.gym.domain;

import com.project.gym.domain.enums.MembershipStatus;
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
    private MembershipStatus membershipStatus; // 1이용중 2종료 3환불

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;

    private String memberName;

}
