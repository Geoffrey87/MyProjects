package com.tourmusic.setlistfinder.mapper;

import com.tourmusic.setlistfinder.dto.BandDTO;
import com.tourmusic.setlistfinder.entitys.Band;
import com.tourmusic.setlistfinder.entitys.Setlist;
import com.tourmusic.setlistfinder.entitys.Song;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BandMapper {

    public static BandDTO domainToDto(Band band, BandDTO bandDto) {
        bandDto.setId(band.getId());
        bandDto.setName(band.getName());

        List<String> songTitles = new ArrayList<>();
        for (Setlist setlist : band.getSetlists()) {
            for (Song song : setlist.getSongs()) {
                songTitles.add(song.getTitle());
            }
        }
        bandDto.setSongs(songTitles);

        return bandDto;
    }

    public static Band dtoToDomain(BandDTO bandDto, Band band) {
        band.setId(bandDto.getId());
        band.setName(bandDto.getName());
        return band;
    }

}
