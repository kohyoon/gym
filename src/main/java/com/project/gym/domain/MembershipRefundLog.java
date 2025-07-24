package com.project.gym.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MembershipRefundLog {
    private Long logId;
    private Long refundId;

    private Integer actionType; // 0: 요청, 1: 승인, 2: 거절, 3: 시스템, 9: 기타
    private String actionDetail;
    private String actionBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate actionAt;
}
