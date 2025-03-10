package com.LegisTrack.LegisTrack.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Values of this class are used to create a new Party object, the values are taken from the user input.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartyInputDto {

    @NotNull(message = "Name cannot be null")
    private String name;

    @Min(value = 1, message = "Number of deputies must be at least 1")
    private int nrOfDeputies;
}
