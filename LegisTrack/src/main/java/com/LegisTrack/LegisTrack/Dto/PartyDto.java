package com.LegisTrack.LegisTrack.Dto;

import lombok.*;

/**
 * This class is the one to be exposed to the user, it contains the values of a Party object.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartyDto {
    private Long id;
    private String name;
    private int nrOfDeputies;
    private String userId;
}

