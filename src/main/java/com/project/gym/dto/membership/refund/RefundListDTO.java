package com.project.gym.dto.membership.refund;

import com.project.gym.domain.enums.RefundStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RefundListDTO {
    private Long refundId;
    private Long membershipId;

    private LocalDate requestedAt;
    private Long requestedBy;

    private String refundReason;

    private LocalDate approvedAt;
    private Long approvedBy;

    private LocalDate rejectedAt;
    private Long rejectedBy;
    private String rejectReason;

    private LocalDate reviewedAt;
    private Long reviewedBy;

    private RefundStatus refundStatus;

    //
    private String memberName;
    private String membershipType;
    private String requestedByName;
    private String approvedByName;
    private String rejectedByName;
    private String reviewedByName;

}
