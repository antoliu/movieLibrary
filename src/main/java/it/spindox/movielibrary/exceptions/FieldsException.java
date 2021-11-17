package it.spindox.movielibrary.exceptions;

import it.spindox.movielibrary.model.ErrorType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode
public class FieldsException extends Exception {

    private int code;
    private String message;
    private HttpStatus status;

    public FieldsException(ErrorType error, HttpStatus status){
        this.code = error.value();
        this.message = error.getMessage();
        this.status = status;
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

    public FieldsException(ErrorType error, String message, HttpStatus status){
        super(message);
        this.code = error.value();
        this.message = message;
        this.status = status;
    }
}

