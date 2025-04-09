package com.paymentsAlert.paymentsAlert.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInputDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "ServiceType ID is required")
    private Long serviceTypeId;

    @Min(value = 1, message = "Amount must be greater than or equal to 1")
    private double amount;

    @NotNull(message = "Date is required")
    private LocalDate dueDate;

    @Pattern(regexp = "MONTHLY|QUARTERLY|SEMI_ANNUALLY|YEARLY", message = "Frequency must be one of MONTHLY, QUARTERLY, SEMI_ANNUALLY, or YEARLY")
    private String recurrencePeriod;
}
