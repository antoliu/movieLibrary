package it.demo.movie.model;

public enum Genre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    FANTASY("Fantasy"),
    HISTORICAL("Historical"),
    HORROR("Horror"),
    ROMANCE("Romance"),
    THRILLER("Thriller"),
    WESTERN("Western");

    private String description;

    Genre(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
