package com.project.gym.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MembershipSuspendHistory {

    private Long suspendId;
    private Long membershipId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate suspendStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate suspendEndDate;
    private String suspendReason;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    private Long createdBy; // 생성한 관리자ID

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;
    private Long updatedBy; // 최종 수정한 관리자ID

    private String membershipType;
    private String memberId;
    private String memberName;

    private String createdByName;
    private String updatedByName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedEndDate;

    private Integer periodDays;



}
