package com.project.gym.domain.enums;

public enum RefundLogType {
    REQUESTED,  // 회원이 환불 요청함
    PENDING,    // 관리자가 검토 시작함
    APPROVED,   // 환불 승인됨
    REJECTED,   // 환불 반려됨
    SYSTEM,     // 시스템 자동 처리
    OTHER;       // 그 외 기타
}
