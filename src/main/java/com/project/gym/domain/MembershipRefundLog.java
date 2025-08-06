package com.project.gym.domain;

import com.project.gym.domain.enums.ActorRole;
import com.project.gym.domain.enums.RefundLogType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MembershipRefundLog {
    private Long logId;
    private Long refundId;

    private RefundLogType logType; // REQUESTED, PENDING, APPROVED, REJECTED, SYSTEM, ETC
    private String logDetail;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate performedAt;
    private Long performedBy; // 관리자면 ADMIN_ID | 회원이면 MEMBER_ID

    private ActorRole actorRole;

    public static MembershipRefundLog from(MembershipRefundHistory refund,
                                           RefundLogType logType, ActorRole actorRole,
                                           Long performedBy) {
        MembershipRefundLog log = new MembershipRefundLog();
        log.setRefundId(refund.getRefundId());
        log.setLogType(logType);
        log.setPerformedBy(performedBy);
        log.setActorRole(actorRole);

        if(logType == RefundLogType.REQUESTED) {
            log.setLogDetail("[환불 요청 사유] " + (refund.getRefundReason() != null ? refund.getRefundReason() : "사유 없음"));
        } else if(logType == RefundLogType.REJECTED) {
            log.setLogDetail("[반려 사유] " + refund.getRejectReason());
        } else if(logType == RefundLogType.APPROVED) {
            log.setLogDetail("[환불 승인 완료]");
        } else if(logType == RefundLogType.PENDING) {
            log.setLogDetail("[관리자 검토중]");
        }

        return log;
    }

}
