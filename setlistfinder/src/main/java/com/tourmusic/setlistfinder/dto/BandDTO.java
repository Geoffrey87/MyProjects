package com.tourmusic.setlistfinder.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BandDTO {
    private Long id;
    private String name;
    private List<String> songs; // Only song titles instead of full Song objects
}
