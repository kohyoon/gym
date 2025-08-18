package com.project.gym.dto.membership;

import com.project.gym.domain.enums.MembershipStatus;
import com.project.gym.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MembershipUpdateFormDTO {

    private Long memberId;
    private String memberName;
    private String memberLoginId;
    private String email;
    private String phone;

    private Long membershipId;
    private String membershipType;
    private Integer periodDays;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private int price;
    private MembershipStatus membershipStatus; // ACTIVE, SUSPENDED, REFUND, FINISHED

    private Long createdBy; // 등록한 관리자 ID
    private String createdByName;

    private Long updatedBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedEndDate; // 정지했으면 추가되는 항목

    private PaymentMethod paymentMethod; // CARD, CASH,  BANK_TRANSFER, ETC

}
