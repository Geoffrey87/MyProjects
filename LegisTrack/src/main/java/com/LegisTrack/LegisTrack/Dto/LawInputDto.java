package com.LegisTrack.LegisTrack.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Values of this class are used to create a new Law object, the values are taken from the user input.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LawInputDto {

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Proposing party ID is required")
    private Long proposingPartyId;

    @NotNull(message = "Proposed date is required")
    private LocalDate dateProposed;
}

