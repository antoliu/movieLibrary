package it.spindox.movielibrary.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = MovieException.class)
    public ResponseEntity<ErrorResponse> exceptionMovieNotFound(MovieException ex){
        System.err.println("===> ERROR: Movie Exception: " + ex.getCode() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<ErrorResponse>(err, ex.getStatus());
    }

    @ExceptionHandler(value = FieldsException.class)
    public ResponseEntity<ErrorResponse> exceptionEmptyRepository(FieldsException ex){
        System.err.println("===> ERROR: Fields Exception: " + ex.getCode() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<ErrorResponse>(err, ex.getStatus());
    }

    @ExceptionHandler(value = RepositoryException.class)
    public ResponseEntity<ErrorResponse> exceptionInvalidFields(RepositoryException ex){
        System.err.println("===> ERROR: Repository Exception: " + ex.getCode() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<ErrorResponse>(err, ex.getStatus());
    }
}
