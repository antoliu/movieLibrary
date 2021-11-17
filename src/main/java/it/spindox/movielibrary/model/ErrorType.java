package it.spindox.movielibrary.model;

public enum ErrorType {

    MOVIE_NOT_FOUND(100, "The ID does not exist in the repository"),
    TITLE_NOT_FOUND(101, "No movies found with the inserted title"),
    DIRECTOR_NOT_FOUND(112, "No movies found for the inserted director"),

    TITLE_OR_DIRECTOR_NOT_VALID(110, "Title and Directors field cannot be empty or null"),
    TITLE_NOT_VALID(111, "Title field cannot be empty or null"),
    DIRECTOR_NOT_VALID(112, "Director field cannot be empty or null"),
    EMPTY_FIELD(113, "The list of movies cannot be empty"),

    EMPTY_REPOSITORY(120, "The repository is empty");

    private final int value;
    private final String message;

    private ErrorType(int value, String message) {
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
