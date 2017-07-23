package org.ksubaka.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.ksubaka.MovieMusicClientApplication;
import org.ksubaka.dtos.MusicResultsDto;
import org.ksubaka.models.Album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.test.util.AssertionErrors.assertEquals;


/**
 * Created by kanke on 23/07/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieMusicClientApplication.class)
public class MusicServiceTest {

    @Autowired
    private MusicService musicService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${resource.music}")
    private String musicResource;

    @Autowired
    private RestTemplate restTemplate;

    static {
        System.setProperty("api", "music");
        System.setProperty("music", "believe");
    }

    @Test
    public void shouldGetMusicAlbums() throws Exception {

        String expectedMusicResultDto = "{\"name\":\"Believe\",\"artist\":\"Disturbed\",\"url\": \"https://www.last.fm/music/Disturbed/Believe\"}";

        MusicResultsDto actualMusicResultsDto = musicService.getAlbums(System.getProperty("music"));

        assertEquals("Expected number of albums", 50, actualMusicResultsDto.getSearch().size());
        assertEquals("Expected first album", objectMapper.readValue(expectedMusicResultDto, Album.class), actualMusicResultsDto.getSearch().get(0));
        assertEquals("Expected third album artist", objectMapper.readValue(expectedMusicResultDto, Album.class).getArtist(), actualMusicResultsDto.getSearch().get(0).getArtist());
    }


    @Test(expected = HttpStatusCodeException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Problem retrieving music albums from API: ")
    public void shouldThrowGetMusicApiException() throws Exception {

        URI uri = UriComponentsBuilder.fromHttpUrl(musicResource)

                // Add query parameter
                .queryParam("method", "album.search")
                .queryParam("album", "kankewewsxx")
                .queryParam("api_key", "no")
                .queryParam("format", "json").build().encode().toUri();

        restTemplate.getForObject(uri, MusicResultsDto.class);
    }

    @AfterClass
    public static void afterClass() {
        System.clearProperty("api");
        System.clearProperty("music");
    }
}
