package com.tourmusic.setlistfinder.service.impl;

import com.tourmusic.setlistfinder.dto.BandDTO;
import com.tourmusic.setlistfinder.entitys.Band;
import com.tourmusic.setlistfinder.exception.BandNotFoundException;
import com.tourmusic.setlistfinder.mapper.BandMapper;
import com.tourmusic.setlistfinder.repo.BandRepo;
import com.tourmusic.setlistfinder.service.IBandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BandService implements IBandService {

    private BandRepo bandRepo;

    private BandMapper bandMapper;

    @Autowired
    public BandService(BandRepo bandRepository, BandMapper bandMapper) {
        this.bandRepo = bandRepository;
        this.bandMapper = bandMapper;
    }

    @Override
    public BandDTO getBandByName(String bandName) {
        Optional<Band> bandOptional = bandRepo.findByName(bandName);

        if (bandOptional.isEmpty()) {
            throw new BandNotFoundException("Band not found: " + bandName);
        } else {
            return bandMapper.domainToDto(bandOptional.get(), new BandDTO());
        }

    }


}
