package it.demo.movie.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@NotNull
@Document("movies")
public class Movie implements Serializable {

    @Id
    private String ID;

    @NotBlank(message = "Movie title cannot be empty or null")
    private String title;

    @NotBlank(message = "Movie director cannot be empty or null")
    private String director;

    @NotNull(message = "Movie genre cannot be empty or null")
    private Genre genre;

    @Range(min = 1900, max = 2050, message = "Movie release year should be from 1900 to 2050")
    private long year;

    public Movie(String title, String director) {
        this.title = title;
        this.director = director;
    }

    public Movie(String id, String title, String director) {
        this.ID = id;
        this.title = title;
        this.director = director;
    }

    public Movie(String title, String director, Genre genre, long year){
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.year = year;
    }
}