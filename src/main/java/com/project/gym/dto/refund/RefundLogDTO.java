package com.project.gym.dto.refund;

import com.project.gym.domain.MembershipRefundHistory;
import com.project.gym.domain.enums.ActorRole;
import com.project.gym.domain.enums.RefundLogType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RefundLogDTO {
    //===== 로그 정보 =====//
    private Long logId;
    private Long refundId;
    private RefundLogType logType;
    private String logDetail;
    private LocalDate performedAt;
    private Long performedBy;
    private ActorRole actorRole;

    //
    private String performedByName;


}
