package com.project.gym.domain;

import com.project.gym.domain.enums.ActorRole;
import com.project.gym.domain.enums.RefundStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MembershipRefundLog {
    private Long logId;
    private Long refundId;

    private Integer logType; // REQUESTED, PENDING, APPROVED, REJECTED, SYSTEM, ETC
    private String logDetail;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate performedAt;
    private String performedBy; // 관리자면 ADMIN_ID | 회원이면 MEMBER_ID

    private ActorRole actorRole;

    private String performedByName; // 관리자면 ADMIN_NAME | 회원이면 MEMBER_NAME

    private String memberName;
    private String membershipType;

    private String refundReason;
    private String rejectReason;
    private Integer refundAmount;
    private RefundStatus refundStatus;

}
