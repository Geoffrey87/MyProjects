package com.paymentsAlert.paymentsAlert.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentOutputDto {
    private double amount;
    private String dueDate;
    private boolean paid;
    private String recurrencePeriod;
}
