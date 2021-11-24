package it.demo.movie.model;

public enum ErrorType {

    MOVIE_NOT_FOUND(100, "The ID does not exist in the repository"),
    TITLE_NOT_FOUND(101, "No movies found with the inserted title"),
    DIRECTOR_NOT_FOUND(112, "No movies found for the inserted director"),
    YEAR_NOT_FOUND(113, "No movies found for the inserted year"),
    GENRE_NOT_FOUND(112, "No movies found for the inserted genre"),

    TITLE_OR_DIRECTOR_NOT_VALID(110, "Title and Directors field cannot be empty or null"),
    TITLE_NOT_VALID(111, "Title field cannot be empty or null"),
    DIRECTOR_NOT_VALID(112, "Director field cannot be empty or null"),
    EMPTY_FIELD(113, "The list of movies cannot be empty"),
    YEAR_NOT_VALID(114, "The year should be between 1900 and 2050"),
    GENRE_NOT_VALID(115, "The inserted genre is not valid"),

    EMPTY_REPOSITORY(120, "The repository is empty"),

    BODY_NOT_VALID(130, "The body request is not valid. It should contain a movie");

    private final int value;
    private final String message;

    ErrorType(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int value() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }
}
