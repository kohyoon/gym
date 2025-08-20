package com.project.gym.dto.admin;

import lombok.Data;

@Data
public class AdminSearchCriteria {
    private String searchType;
    private String keyword;

    // 페이징
    private Integer page = 1;
    private Integer size = 20;
    public int getOffset() {
        return (Math.max(1, page) - 1) * Math.max(1, size);
    }

    // 체크 시 퇴사 회원 제외
    private Boolean excludeResigned = false; // 기본값 false
}
