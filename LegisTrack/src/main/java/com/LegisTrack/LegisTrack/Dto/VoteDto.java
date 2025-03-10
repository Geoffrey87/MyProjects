package com.LegisTrack.LegisTrack.Dto;

import lombok.*;

/**
 * This class is the one to be exposed to the user, it contains the values of a Vote object.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {
    private Long lawId;
    private Long id;
    private String description;
    private String voteType;
}

