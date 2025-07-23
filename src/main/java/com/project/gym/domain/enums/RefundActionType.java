package com.project.gym.domain.enums;

public enum RefundActionType {
    REQUESTED(0),
    APPROVED(1),
    REJECTED(2),
    SYSTEM(3),
    OTHER(9);

    private final int code;

    RefundActionType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static RefundActionType fromCode(int code) {
        for(RefundActionType type : values()) {
            if(type.code == code) return type;
        }
        throw new IllegalArgumentException("Invalid RefundActionType code:" + code);
    }
}
