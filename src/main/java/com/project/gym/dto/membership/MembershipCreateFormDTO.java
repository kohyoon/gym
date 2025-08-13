package com.project.gym.dto.membership;

import com.project.gym.domain.enums.MembershipStatus;
import com.project.gym.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MembershipCreateFormDTO {

    private Long memberId;
    private String memberName;
    private String memberLoginId;
    private String email;
    private String phone;

    @NotBlank(message = "회원권 종류를 선택해주세요")
    private String membershipType;
    @NotNull(message = "이용 기간을 선택해주세요")
    private Integer periodDays;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private int price;
    private MembershipStatus membershipStatus; // ACTIVE, SUSPENDED, REFUND, FINISHED

    private Long createdBy; // 등록한 관리자 ID

    private PaymentMethod paymentMethod; // CARD, CASH,  BANK_TRANSFER, ETC

}
