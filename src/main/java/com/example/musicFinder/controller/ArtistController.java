package com.example.musicFinder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class ArtistController {

    RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/artist/{name}")
    public ResponseEntity<String> getArtistInfo(@PathVariable String name) {
        String url = "https://en.wikipedia.org/api/rest_v1/page/summary/" + name;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return ResponseEntity.status(HttpStatus.OK).body(response.getBody());  // 200 OK if found
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artist not found."); //400 not found
            }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving artist info.");//500 Otherwise
            }
        }
    }
}
