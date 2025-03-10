package com.LegisTrack.LegisTrack.Dto;


import lombok.*;

/**
 * This class is the one to be exposed to the user, it contains the values of a VoteCount object.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteCountDto {
    private int inFavor;
    private int against;
    private int abstention;
}
