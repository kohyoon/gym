package com.project.gym.dto.member;

import com.project.gym.domain.enums.MemberSearchField;
import lombok.Data;

@Data
public class MemberSearchCondition {
    private MemberSearchField searchType;
    private String keyword;

    // 페이징
    private Integer page = 1;
    private Integer size = 20;
    public int getOffset() {
        return (Math.max(1, page) - 1) * Math.max(1, size);
    }

}
