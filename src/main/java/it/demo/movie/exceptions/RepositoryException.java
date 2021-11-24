package it.demo.movie.exceptions;

import it.demo.movie.model.ErrorType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode
public class RepositoryException extends Exception {

    private int code;
    private String message;
    private HttpStatus status;

    public RepositoryException(ErrorType error, HttpStatus status){
        this.code = error.value();
        this.message = error.getMessage();
        this.status = status;
    }

    public RepositoryException(String message) {
        super(message);
        this.message = message;
    }

    public RepositoryException(ErrorType error, String message){
        super(message);
        this.code = error.value();
        this.message = message;
    }

    public RepositoryException(ErrorType error, String message, HttpStatus status){
        super(message);
        this.code = error.value();
        this.message = message;
        this.status = status;
    }
}
