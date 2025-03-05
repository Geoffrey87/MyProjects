package com.tourmusic.setlistfinder.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SetlistDTO {
    private Long id;
    private String location;
    private LocalDate date;
    private String bandName;  // Instead of full Band object, just the name
    private List<String> songs; // Only song titles
}
