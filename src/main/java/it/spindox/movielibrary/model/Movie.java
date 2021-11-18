package it.spindox.movielibrary.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {

    private int ID;
    private String title;
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

    public Movie(String title, String director, int id) {
        this.ID = id;
        this.title = title;
        this.director = director;
    }


    public int getId() {
        return ID;
    }

    public void setId(int id) {
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
