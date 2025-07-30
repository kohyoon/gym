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
    private LocalDate recordedAt;

    private Long adminId; // 담당자(수정한 사람)

}
