package com.paymentsAlert.paymentsAlert.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTypeInputDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "name is required") // e.g. "Internet", "Phone", "TV", "Insurance", etc.
    private String name;
}
