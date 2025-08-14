package com.project.gym.dto.membership;

import com.project.gym.domain.enums.MembershipStatus;
import com.project.gym.domain.enums.PaymentMethod;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MembershipDetailDTO {

    private Long membershipId;
    private Long memberId;
    private String memberName;
    private String membershipType;
    private Integer periodDays;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private int price;
    private MembershipStatus membershipStatus; // ACTIVE, SUSPENDED, REFUND, FINISHED

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDate createdAt;
    private Long createdBy; // 등록한 관리자 ID
    private String createdByName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDate updatedAt;
    private Long updatedBy; // 수정한 관리자 ID
    private String updatedByName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedEndDate;

    private PaymentMethod paymentMethod; // CARD, CASH,  BANK_TRANSFER, ETC

    
}
