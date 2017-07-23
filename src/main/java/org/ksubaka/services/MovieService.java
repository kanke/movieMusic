package org.ksubaka.services;

import org.ksubaka.dtos.MovieResultsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;


/**
 * Created by kanke on 23/07/2017.
 */

@Service
public class MovieService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${resource.movie}")
    private String movieResource;

    @Value("${omdb.apikey}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;


    public MovieResultsDto getMovies(String searchQuery) throws IOException {

        MovieResultsDto movieResults = null;
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(movieResource)

                    // Add query parameter
                    .queryParam("s", searchQuery)
                    .queryParam("apikey", apiKey)
                    .queryParam("plot", "full")
                    .queryParam("i", "tt3896198").build().encode().toUri();

            movieResults = restTemplate.getForObject(uri, MovieResultsDto.class);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Movie object retrieved without errors");
            }

        } catch (HttpStatusCodeException exception) {
            LOGGER.error("Problem retrieving movie results from API: ", exception.getMessage(), exception.getStatusCode());
        }

        return movieResults;
    }
}
