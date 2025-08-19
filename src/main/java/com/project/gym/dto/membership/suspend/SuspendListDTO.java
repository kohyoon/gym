package com.project.gym.dto.membership.suspend;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class SuspendListDTO {

    private Long suspendId;
    private Long membershipId;
    private String membershipType; // PT, pilates, gym

    private Long memberId;
    private String memberName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate suspendStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate suspendEndDate;
    private String suspendReason;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    private Long createdBy; // 생성한 관리자ID
    private String createdByName;

}
