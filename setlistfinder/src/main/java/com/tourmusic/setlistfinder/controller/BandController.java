package com.tourmusic.setlistfinder.controller;

import com.tourmusic.setlistfinder.dto.BandDTO;
import com.tourmusic.setlistfinder.service.IBandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bands")
public class BandController {

    private IBandService bandService;

    @Autowired
    public BandController(IBandService bandService) {
        this.bandService = bandService;
    }


    @GetMapping
    public BandDTO getBandByName(@RequestParam String name) {
        return bandService.getBandByName(name);
    }
}
