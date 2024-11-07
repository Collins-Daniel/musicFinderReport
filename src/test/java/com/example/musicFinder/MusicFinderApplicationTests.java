package com.example.musicFinder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;

@SpringBootTest
class MusicFinderApplicationTests {

	@Autowired
	private MusicFinderController controller;
	@Autowired
	private MusicFinderApplication application;

	@Test
	void contextLoads() {
	}



	@Test
    public void testValidSongLyrics() {
        String artist = "Thin Lizzy";
        String song = "Dancing In The Moonlight";

        ObjectNode response = controller.getSongDetails("Thin Lizzy", "Dancing In The Moonlight");

        assertNotNull(response, "Expected a response");
		response.fieldNames().forEachRemaining(field -> {
            JsonNode fieldValue = response.get(field);
        	assertNotNull(fieldValue, "Field " + field + " should not be null");
		});

		assertEquals(artist, response.get("artist").asText(), "Artist should be 'Thin Lizzy'");

        // Validate that the song name is correct
        assertEquals(song, response.get("song").asText(), "Song should be 'Dancing In The Moonlight'");

        // Validate that the YouTube search URL starts with the correct base URL
        String youtubeSearchUrl = response.get("youtubeSearch").asText();
        assertTrue(youtubeSearchUrl.startsWith("https://www.youtube.com/results?search_query="),
                "YouTube search URL should start with the correct base URL");

        // Validate that lyrics are not null and contain some content (since we're mocking the response, you can use a substring check here)
        String lyrics = response.get("lyrics").asText();
        assertNotNull(lyrics, "Lyrics should not be null");
        assertTrue(lyrics.contains("When I passed you in the doorway"), "Lyrics should contain part of the song lyrics");
    }

	@Test
    public void testInvalidSongLyrics() {
        ObjectNode response = controller.getSongDetails("Big Lizzy", "Dancing In The Moonlight");

        // Assert error message in response
        assertNotNull(response, "Response should not be null");
        assertEquals("{\"error\":\"Lyrics not found\"}", response.get("lyrics").asText(), "Expected error message for unknown artist");
		ObjectNode response0 = controller.getSongDetails("Thin Lizzy", "Dancing in the sunlight");

        // Assert error message in response
        assertNotNull(response0, "Response should not be null");
        assertEquals("{\"error\":\"Lyrics not found\"}", response0.get("lyrics").asText(), "Expected error message for unknown song");

        // Assert error message in response
        assertThrows(NullPointerException.class, ()-> {
			controller.getSongDetails(null, "Dancing In The Moonlight");
		});

		 assertThrows(NullPointerException.class, ()-> {
			controller.getSongDetails("Thin Lizzy", null);
		});

		ObjectNode response1 = controller.getSongDetails("", "Dancing In The Moonlight");

        // Assert error message in response
        assertNotNull(response1, "Response should not be null");
        assertEquals("{\"error\":\"Lyrics not found\"}", response1.get("lyrics").asText(), "Expected error message for unknown artist");
		
		ObjectNode response2 = controller.getSongDetails("Thin Lizzy", "");

        // Assert error message in response
        assertNotNull(response2, "Response should not be null");
        assertEquals("{\"error\":\"Lyrics not found\"}", response2.get("lyrics").asText(), "Expected error message for unknown song");
    }
}
