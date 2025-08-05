package com.project.gym.domain;

import com.project.gym.domain.enums.RefundStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MembershipRefundHistory {

    private Long refundId;
    private Long membershipId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate requestedAt;
    private Long requestedBy;
    private String refundReason;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate approvedAt;
    private Long approvedBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rejectedAt;
    private Long rejectedBy;
    private String rejectReason;

    private Integer refundAmount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reviewedAt;
    private Long reviewedBy;

    private RefundStatus refundStatus;

    private String memberName;
    private String membershipType;
    private String periodDays;
    private Integer price; // 회원권 가격

    private String requestedByName;
    private String approvedByName;
    private String rejectedByName;
    private String reviewedByName;
}
