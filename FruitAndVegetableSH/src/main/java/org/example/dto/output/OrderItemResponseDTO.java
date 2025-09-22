package org.example.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an individual item included in an order,
 * typically used when returning order details to the client.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private String productName;
    private int quantity;
    private double price;
    private double total;
}
