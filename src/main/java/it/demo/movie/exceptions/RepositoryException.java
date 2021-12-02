package it.demo.movie.exceptions;

import it.demo.movie.model.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@Getter
@AllArgsConstructor
public class RepositoryException extends Exception {

    private int code;
    private String message;
    private HttpStatus status;

    public RepositoryException(ErrorType error){
        this.code = error.value();
        this.message = error.getMessage();
        this.status = error.getStatus();
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
}
