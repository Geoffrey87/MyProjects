package com.paymentsAlert.paymentsAlert.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInputDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Description is required")
    private String description;

    @Min(value = 1, message = "Amount must be greater than or equal to 1")
    private double amount;

    @NotNull(message = "Date is required")
    private LocalDateTime dueDate;

    @NotNull(message = "Recurrence period is required")
    @Pattern(regexp = "NONE|MONTHLY|QUARTERLY|SEMI_ANNUALLY|YEARLY", message = "Choose one frequency: NONE, MONTHLY, QUARTERLY, SEMI_ANNUALLY, YEARLY")
    private String recurrencePeriod;
}
