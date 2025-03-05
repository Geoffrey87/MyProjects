package com.tourmusic.setlistfinder.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourmusic.setlistfinder.dto.BandDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExternalApiService {

    private static final String API_URL = "https://api.setlist.fm/rest/1.0";
    private static final String API_KEY = "\t\n" +
            "qvb0exDxmBsVa9OfIxVgs5TDVxW4MJ4rPoOx";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ExternalApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public BandDTO fetchBandFromApi(String bandName) {
        String mbid = getBandMbid(bandName);
        if (mbid == null) {
            return null; // Band not found
        }
        return getBandSetlists(mbid, bandName);
    }

    private String getBandMbid(String bandName) {
        String url = API_URL + "/search/artists?artistName=" + bandName;

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode artists = root.path("artist");

                if (artists.isArray() && artists.size() > 0) {
                    return artists.get(0).path("mbid").asText(); // Get first artist's mbid
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private BandDTO getBandSetlists(String mbid, String bandName) {
        String url = API_URL + "/artist/" + mbid + "/setlists";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return parseSetlistResponse(response.getBody(), bandName);
        }

        return null;
    }

    private BandDTO parseSetlistResponse(String jsonResponse, String bandName) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            List<String> songTitles = new ArrayList<>();

            JsonNode setlists = root.path("setlist");
            for (JsonNode setlist : setlists) {
                JsonNode songs = setlist.path("sets").path("set");
                for (JsonNode set : songs) {
                    for (JsonNode song : set.path("song")) {
                        songTitles.add(song.path("name").asText());
                    }
                }
            }

            BandDTO bandDTO = new BandDTO();
            bandDTO.setName(bandName);
            bandDTO.setSongs(songTitles);
            return bandDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
