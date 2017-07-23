package org.ksubaka.services;

import org.ksubaka.dtos.MusicResultsDto;
import org.ksubaka.models.Album;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by kanke on 23/07/2017.
 */

@Service
public class MusicService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${resource.music}")
    private String musicResource;

    @Value("${lastfm.apikey}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    private MusicResultsDto musicResultsDto;

    public MusicResultsDto getAlbums(String searchQuery) throws IOException {

        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(musicResource)

                    // Add query parameter
                    .queryParam("method", "album.search")
                    .queryParam("album", searchQuery)
                    .queryParam("api_key", apiKey)
                    .queryParam("format", "json").build().encode().toUri();

            Map response = restTemplate.getForObject(uri, HashMap.class);
            Map results = (Map) response.get("results");
            Map albumMatches = (Map) results.get("albummatches");
            List albumMaps = (List) albumMatches.get("album");
            List<Album> albums = createAlbums(albumMaps);

            musicResultsDto = new MusicResultsDto();
            musicResultsDto.setSearch(albums);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Music object retrieved without errors");
            }

        } catch (HttpStatusCodeException exception) {
            System.err.println("Problem retrieving music albums from API: " + exception.getMessage());
            LOGGER.error("Problem retrieving music albums from API: ", exception.getMessage(), exception.getStackTrace());
        }

        return musicResultsDto;
    }

    private List<Album> createAlbums(List<Map> albumMaps) {
        return albumMaps.stream().map(a -> new Album((String) a.get("name"), (String) a.get("artist"), (String) a.get("url")))
                .collect(Collectors.toList());
    }
}
