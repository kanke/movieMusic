package org.ksubaka.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by kanke on 23/07/2017.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @JsonProperty(value = "Title")
    private String title;

    @JsonProperty(value = "Year")
    private String year;

    @JsonProperty(value = "Type")
    private String type;

    @JsonProperty(value = "Poster")
    private String poster;

    @Override
    public String toString() {
        return "\r\n" + " {" +
                "Title:'" + title + '\'' +
                ", Year:'" + year + '\'' +
                ", Type:" + type +
                ", Poster:'" + poster + '\'' +
                '}' + "\r\n";
    }
}