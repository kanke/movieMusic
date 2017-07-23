package org.ksubaka.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ksubaka.models.Movie;

import java.util.List;

/**
 * Created by kanke on 23/07/2017.
 */

@Data
@AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResultsDto {

    @JsonProperty(value = "Search")
    List<Movie> search;

    public String toString() {
        String results = "Movies" + "\r\n";
        for (Movie movie : search) {
            results += movie.toString();
        }
        return results;
    }
}

