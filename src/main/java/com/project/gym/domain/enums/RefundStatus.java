package com.project.gym.domain.enums;

public enum RefundStatus {

    REQUESTED(0, "환불 요청"),
    APPROVED(1, "환불 승인"),
    REJECTED(2, "환불 거절");

    private final int code;
    private final String description;

    RefundStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RefundStatus fromCode(int code) {
        for (RefundStatus status : RefundStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid refund status code: " + code);
    }
}
