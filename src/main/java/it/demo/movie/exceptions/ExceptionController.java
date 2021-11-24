package it.demo.movie.exceptions;

import it.demo.movie.model.ErrorType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;



@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = MovieException.class)
    public ResponseEntity<ErrorResponse> exceptionMovieNotFound(MovieException ex){
        System.err.println("===> ERROR: Movie Exception: " + ex.getCode() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(err, ex.getStatus());
    }

    @ExceptionHandler(value = FieldsException.class)
    public ResponseEntity<ErrorResponse> exceptionEmptyRepository(FieldsException ex){
        System.err.println("===> ERROR: Fields Exception: " + ex.getCode() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(err, ex.getStatus());
    }

    @ExceptionHandler(value = RepositoryException.class)
    public ResponseEntity<ErrorResponse> exceptionInvalidFields(RepositoryException ex){
        System.err.println("===> ERROR: Repository Exception: " + ex.getCode() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(err, ex.getStatus());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> exceptionConstraintViolation(ConstraintViolationException ex){
        System.err.println("===> ERROR: Constraint Exception: " + ErrorType.TITLE_OR_DIRECTOR_NOT_VALID.value() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ErrorType.TITLE_OR_DIRECTOR_NOT_VALID.value(), ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /* TODO documentazione*/
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.err.println("===> ERROR: Constraint Exception: " + ErrorType.BODY_NOT_VALID.value() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ErrorType.BODY_NOT_VALID.value(), ErrorType.BODY_NOT_VALID.getMessage());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
