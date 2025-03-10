package com.LegisTrack.LegisTrack.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * Values of this class are used to create a new Vote object, the values are taken from the user input.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteInputDto {

    @NotNull(message = "Law ID cannot be null")
    private Long lawId;

    @NotBlank(message = "Vote type cannot be blank")
    @Pattern(
            regexp = "IN_FAVOR|AGAINST|ABSTENTION",
            message = "Vote type must be one of: IN_FAVOR, AGAINST, ABSTENTION"
    )
    private String voteType;
}


