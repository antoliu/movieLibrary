package it.demo.movie.exceptions;


import it.demo.movie.model.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@Getter
@AllArgsConstructor
public class EntityException extends Exception {

    private int code;
    private String message;
    private HttpStatus status;

    public EntityException(ErrorType error){
        this.code = error.value();
        this.message = error.getMessage();
        this.status = error.getStatus();
    }

    public EntityException(String message) {
        super(message);
        this.message = message;
    }

    public EntityException(ErrorType error, String message){
        super(message);
        this.code = error.value();
        this.message = message;
    }
}
