package org.ksubaka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.ksubaka.services.MovieService;
import org.ksubaka.services.MusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * Created by kanke on 23/07/2017.
 */

@SpringBootApplication
public class MovieMusicClientApplication implements CommandLineRunner {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MusicService musicService;

    @Autowired
    MovieService movieService;

    @Value("${movie.property}")
    private String movieProperty;

    @Value("${music.property}")
    private String musicProperty;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MovieMusicClientApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Override
    public void run(String... strings) throws Exception {

        String apiProperty = System.getProperty("api");
        LOGGER.info("Application started with command-line arguments: {}. \n To kill this application, press Ctrl + C.", apiProperty);

        if (apiProperty.equals(musicProperty)) {
            String music = System.getProperty(musicProperty);
            if (StringUtils.isNotEmpty(music)) {
                System.out.println(musicService.getAlbums(music));
            }
        } else if (apiProperty.equals(movieProperty)) {
            String movie = System.getProperty(movieProperty);
            if (StringUtils.isNotEmpty(movie)) {
                System.out.println(movieService.getMovies(movie));
            }
        } else {
            throw new IllegalArgumentException("This application doesn't support unknown apis, please use movie or music");
        }

    }
}




