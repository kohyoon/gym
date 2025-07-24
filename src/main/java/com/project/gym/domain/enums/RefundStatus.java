package com.project.gym.domain.enums;

public enum RefundStatus {
    REQUESTED(0),
    COMPLETED(1),
    REJECTED(2);

    private final int code;

    RefundStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static RefundStatus fromCode(int code) {
        for (RefundStatus status : values()) {
            if (status.code == code) return status;
        }
        throw new IllegalArgumentException("Invalid refund status code: " + code);
    }
}
