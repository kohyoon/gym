package com.project.gym.dto.membership;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class MembershipSearchCriteria {

    private String keyword;

    private Long memberId;
    private Long membershipId;

    // 필터
    private List<String> membershipStatuses; // NORMAL, SUSPENDED, PENDING, APPROVED, REJECTED, FINISHED
    private List<String> types; // gym, pilates, PT

    // 날짜 범위
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateTo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDateTo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdTo;

    private Integer priceMin;
    private Integer priceMax;
    private Integer remainingDaysMax; // ex. 7이면 7일 이하만(만료임박)

    private Long createdBy;
    private String createdByName;

    // 정렬, 페이징
    private String sortBy; // endDate, startDate, createdAt, memberName 등
    private String sortDir; // ASC, DESC
    private Integer page;
    private Integer size;
    private Integer offset;

}
