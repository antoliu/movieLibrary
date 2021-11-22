package it.spindox.movielibrary.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document("movies")
public class Movie {

    @Id
    private String ID;

    @NotBlank(message = "Movie title cannot be empty or null")
    private String title;

    @NotBlank(message = "Movie director cannot be empty or null")
    private String director;
    /*private String genre;
    private String year;*/

    public Movie(){
    }

    /*public Movie(String title, String director, String genre, String year){
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.year = year;
    }*/

    public Movie(String title, String director) {
        this.title = title;
        this.director = director;
    }

    public Movie(String id, String title, String director) {
        this.ID = id;
        this.title = title;
        this.director = director;
    }


    public String getId() {
        return ID;
    }

    public void setId(String id) {
        this.ID = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    /*public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }*/
}
