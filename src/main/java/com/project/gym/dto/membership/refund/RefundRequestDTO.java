package com.project.gym.dto.membership.refund;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RefundRequestDTO {

    private Long membershipId;

    private String memberName;
    private String membershipType;
    private Integer membershipPrice;

    private Integer expectedRefundAmount;

    private String refundReason;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer periodDays;
}
