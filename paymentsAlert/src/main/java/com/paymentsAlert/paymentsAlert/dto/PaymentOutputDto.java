package com.paymentsAlert.paymentsAlert.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for payment output.
 * The server responds with the payment ID, description, value, date and recurrence period.
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOutputDto {

    private Long id;
    private String description;
    private double amount;
    private String date;
    private boolean paid;
}
