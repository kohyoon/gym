package com.project.gym.dto.suspend;

import lombok.Data;

@Data
public class SuspendSearchCriteria {
    private String searchType;
    private String keyword;

    private Long membershipId;
    private String memberName;
    private String createdByName;

    // 페이징
    private Integer page = 1;
    private Integer size = 20;
    public int getOffset() {
        return (Math.max(1, page) - 1) * Math.max(1, size);
    }
}
