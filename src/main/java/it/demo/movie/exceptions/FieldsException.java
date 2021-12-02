package it.demo.movie.exceptions;

import it.demo.movie.model.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@Getter
@AllArgsConstructor
public class FieldsException extends Exception {

    private int code;
    private String message;
    private HttpStatus status;

    public FieldsException(ErrorType error){
        this.code = error.value();
        this.message = error.getMessage();
        this.status = error.getStatus();
    }

    public FieldsException(String message) {
        super(message);
        this.message = message;
    }

    public FieldsException(ErrorType error, String message){
        super(message);
        this.code = error.value();
        this.message = message;
    }

}

