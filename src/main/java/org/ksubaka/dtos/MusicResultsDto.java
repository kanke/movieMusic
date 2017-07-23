package org.ksubaka.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ksubaka.models.Album;

import java.util.List;


/**
 * Created by kanke on 23/07/2017.
 */

@Data
@AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicResultsDto {

    @JsonProperty(value = "Search")
    List<Album> search;

    public String toString() {
        String results = "Albums" + "\r\n";
        for (Album album : search) {
            results += album.toString();
        }
        return results;
    }
}
