package org.example.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a single item in a customer's order.
 * The client provides the product ID and the desired quantity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    private Long productId;
    private int quantity;
}
