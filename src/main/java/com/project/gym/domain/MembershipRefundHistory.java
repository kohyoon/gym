package com.project.gym.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MembershipRefundHistory {

    private Long refundId;
    private Long membershipId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate requestedAt;
    private String requestedBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate processedAt;
    private String approvedBy;
    private String rejectedBy;

    private String refundReason;
    private String rejectReason;
    private Integer refundAmount;
    private Integer refundStatus; // 0요청, 1승인, 2반려

}
