package it.demo.movie.model;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    /* MOVIES - NOT FOUND */
    MOVIE_NOT_FOUND(104, "The Movie ID does not exist in the repository", HttpStatus.NOT_FOUND),
    TITLE_NOT_FOUND(105, "No movies found with the inserted title", HttpStatus.NOT_FOUND),
    DIRECTOR_NOT_FOUND(106, "No movies found for the inserted director", HttpStatus.NOT_FOUND),
    YEAR_NOT_FOUND(107, "No movies found for the inserted year", HttpStatus.NOT_FOUND),
    GENRE_NOT_FOUND(108, "No movies found for the inserted genre", HttpStatus.NOT_FOUND),

    /* CINEMAS - NOT FOUND */
    CINEMA_NOT_FOUND(109, "The Cinema ID does not exist in the repository", HttpStatus.NOT_FOUND),

    /* PROJECTIONS - NOT FOUND */
    CINEMA_PROJECTIONS_NOT_FOUND(110, "The movie has no projection in any cinema", HttpStatus.NOT_FOUND),

    EMPTY_REPOSITORY(119, "The repository is empty", HttpStatus.NOT_FOUND),

    /* MOVIES - BAD REQUEST */
    TITLE_OR_DIRECTOR_NOT_VALID(120, "Title and Directors field cannot be empty or null", HttpStatus.BAD_REQUEST),
    TITLE_NOT_VALID(121, "Title field cannot be empty or null", HttpStatus.BAD_REQUEST),
    DIRECTOR_NOT_VALID(122, "Director field cannot be empty or null", HttpStatus.BAD_REQUEST),
    EMPTY_FIELD(123, "The list of movies cannot be empty", HttpStatus.BAD_REQUEST),
    YEAR_NOT_VALID(124, "The year should be between 1900 and 2050", HttpStatus.BAD_REQUEST),
    GENRE_NOT_VALID(125, "The inserted genre is not valid", HttpStatus.BAD_REQUEST),

    BODY_NOT_VALID(140, "The body request is not valid. It should contain a movie", HttpStatus.BAD_REQUEST),
    MISSING_PARAM(141, "Missing parameter: ", HttpStatus.BAD_REQUEST);

    private final int value;
    private final String message;
    private final HttpStatus status;

    ErrorType(int value, String message, HttpStatus status) {
        this.value = value;
        this.message = message;
        this.status = status;
    }

    public int value() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getStatus(){
        return this.status;
    }
}
