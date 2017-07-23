package org.ksubaka;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import org.ksubaka.services.MovieService;
import org.ksubaka.services.MusicService;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by kanke on 23/07/2017.
 */

@RunWith(SpringRunner.class)
public class MovieMusicClientApplicationTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @InjectMocks
    private MovieMusicClientApplication application;

    static {
        System.setProperty("music", "believe");
        System.setProperty("movie", "believe");
    }

    @Test
    public void shouldCallMusicServiceWithRightAPIParameter() throws Exception {
        System.setProperty("api", "music");
        MusicService musicService = Mockito.mock(MusicService.class);
        String musicProperty  = System.getProperty("believe");

        musicService.getAlbums(musicProperty);

        verify(musicService, times(1)).getAlbums(musicProperty);
    }

    @Test
    public void shouldCallMovieServiceWithRightAPIParameter() throws Exception {
        System.setProperty("api", "movie");
        MovieService movieService = Mockito.mock(MovieService.class);
        String movieProperty  = System.getProperty("harrypotter");

        movieService.getMovies(movieProperty);

        verify(movieService, times(1)).getMovies(movieProperty);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWithUnknownAPIParameter() throws Exception {
        System.setProperty("api", "fake");
        MovieService movieService = Mockito.mock(MovieService.class);
        String movieProperty  = System.getProperty("fake");

        application.run();
        movieService.getMovies(movieProperty);

        verify(movieService, times(0)).getMovies(movieProperty);
    }

    @AfterClass
    public static void afterClass() {
        System.clearProperty("music");
        System.clearProperty("movie");
    }
}
