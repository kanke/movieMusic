package org.ksubaka.services;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.ksubaka.MovieMusicClientApplication;
import org.ksubaka.dtos.MovieResultsDto;
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
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Value("${resource.movie}")
    private String movieResource;

    @Autowired
    private RestTemplate restTemplate;

    static {
        System.setProperty("api", "movie");
        System.setProperty("movie", "harry potter");
    }

    @Test
    public void shouldGetMovies() throws Exception {

        MovieResultsDto actualMovieResultsDto = movieService.getMovies(System.getProperty("movie"));

        String expectedMovieResultDtoFirstTitle = "Harry Potter and the Deathly Hallows: Part 2";
        String expectedMovieResultDtoThirdYear = "2002";

        assertEquals("Expected number of movies", 10, actualMovieResultsDto.getSearch().size());
        assertEquals("Expected first title", expectedMovieResultDtoFirstTitle, actualMovieResultsDto.getSearch().get(0).getTitle());
        assertEquals("Expected third year", expectedMovieResultDtoThirdYear, actualMovieResultsDto.getSearch().get(2).getYear());
    }

    @Test(expected = HttpStatusCodeException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Problem retrieving movie results from API:")
    public void shouldThrowGetMoviesApiException() throws Exception {

        URI uri = UriComponentsBuilder.fromHttpUrl(movieResource)

                // Add query parameter
                .queryParam("s", System.getProperty("movie"))
                .queryParam("apikey", "fake")
                .queryParam("plot", "full")
                .queryParam("i", "tt3896198").build().encode().toUri();

        restTemplate.getForObject(uri, MovieResultsDto.class);
    }


    @AfterClass
    public static void afterClass() {
        System.clearProperty("api");
        System.clearProperty("movie");
    }
}