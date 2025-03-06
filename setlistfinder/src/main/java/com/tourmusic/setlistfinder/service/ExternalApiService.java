package com.tourmusic.setlistfinder.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourmusic.setlistfinder.dto.BandDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

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
        String searchUrl = API_URL + "/search/artists?artistName=" + bandName;

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> searchResponse = restTemplate.exchange(searchUrl, HttpMethod.GET, entity, String.class);

        if (searchResponse.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode root = objectMapper.readTree(searchResponse.getBody());
                JsonNode artists = root.path("artist");

                for (JsonNode artist : artists) {
                    String name = artist.path("name").asText();
                    String mbid = artist.path("mbid").asText();

                    // 🔎 Verifica se o nome é EXATAMENTE igual antes de continuar
                    if (name.equalsIgnoreCase(bandName) && mbid != null && !mbid.isEmpty()) {
                        // Agora vamos buscar mais detalhes da banda com o MBID
                        if (isOriginalBand(mbid, bandName)) {
                            return mbid; // Retorna apenas se for a banda verdadeira
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Band not found: " + bandName);
    }

    /**
     * Faz uma chamada extra à API para confirmar que esta é a banda original.
     */
    private boolean isOriginalBand(String mbid, String bandName) {
        String artistUrl = API_URL + "/artist/" + mbid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> artistResponse = restTemplate.exchange(artistUrl, HttpMethod.GET, entity, String.class);

        if (artistResponse.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode artist = objectMapper.readTree(artistResponse.getBody());

                String name = artist.path("name").asText();
                String disambiguation = artist.path("disambiguation").asText("");

                // 🔹 Se o nome for exato e não houver "disambiguation", então é a banda original
                return name.equalsIgnoreCase(bandName) && (disambiguation == null || disambiguation.isEmpty());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
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
