package com.project.gym.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MembershipSuspendHistory {

    private Long historyId;
    private Long membershipId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate suspendStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate suspendEndDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate recordedAt;
    private String recordedBy; // 담당자(수정한 사람)


}
