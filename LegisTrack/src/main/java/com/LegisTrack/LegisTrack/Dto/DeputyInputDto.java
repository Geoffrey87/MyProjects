package com.LegisTrack.LegisTrack.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeputyDto {
    private Long id;
    private String name;
    private Long partyId;
    private String partyName;
}
