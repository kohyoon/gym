package com.project.gym.dto.refund;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.enums.RefundStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RefundDetailDTO {
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

    private Long refundAmount;

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

    private List<RefundLogDTO> logs;

    public static RefundDetailDTO fromEntity(MembershipRefundHistory refund, List<RefundLogDTO> logs) {
        RefundDetailDTO dto = new RefundDetailDTO();
        dto.setRefundId(refund.getRefundId());
        dto.setMembershipId(refund.getMembershipId());

        dto.setRequestedAt(refund.getRequestedAt());
        dto.setRequestedBy(refund.getRequestedBy());
        dto.setRefundReason(refund.getRefundReason());
        dto.setApprovedAt(refund.getApprovedAt());
        dto.setApprovedBy(refund.getApprovedBy());
        dto.setRejectedAt(refund.getRejectedAt());
        dto.setRejectedBy(refund.getRejectedBy());
        dto.setRejectReason(refund.getRejectReason());

        dto.setReviewedAt(refund.getReviewedAt());
        dto.setReviewedBy(refund.getReviewedBy());
        dto.setRefundStatus(refund.getRefundStatus());

        dto.setLogs(logs);

        return dto;
    }
}
