package com.tourmusic.setlistfinder.service;

import com.tourmusic.setlistfinder.dto.BandDTO;

public interface IBandService {
    BandDTO getBandByName(String name);
}
