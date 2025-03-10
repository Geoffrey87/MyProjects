package com.LegisTrack.LegisTrack.Dto;

import lombok.*;

import java.time.LocalDate;

/**
 * This class is the one to be exposed to the user, it contains the values of a Law object.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LawDto {
    private Long id;
    private String description;
    private String proposingPartyName;
    private LocalDate dateProposed;
}

