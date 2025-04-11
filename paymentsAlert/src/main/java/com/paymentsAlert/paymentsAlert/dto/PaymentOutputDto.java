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
public class PaymentOutputDto {

    private Long id;
    private String description;
    private double amount;
    private String dueDate;
    private boolean paid;
}
