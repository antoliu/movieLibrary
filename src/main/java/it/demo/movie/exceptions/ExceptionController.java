package it.demo.movie.exceptions;

import it.demo.movie.model.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;



@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(value = EntityException.class)
    public ResponseEntity<ErrorResponse> exceptionMovieNotFound(EntityException ex){
         LOGGER.error("===> ERROR: Movie Exception: " + ex.getCode() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(err, ex.getStatus());
    }

    @ExceptionHandler(value = FieldsException.class)
    public ResponseEntity<ErrorResponse> exceptionEmptyRepository(FieldsException ex){
        LOGGER.error("===> ERROR: Fields Exception: " + ex.getCode() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(err, ex.getStatus());
    }

    @ExceptionHandler(value = RepositoryException.class)
    public ResponseEntity<ErrorResponse> exceptionInvalidFields(RepositoryException ex){
        LOGGER.error("===> ERROR: Repository Exception: " + ex.getCode() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(err, ex.getStatus());
    }

    //TODO documentazione
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> exceptionConstraintViolation(ConstraintViolationException ex){
        LOGGER.error("===> ERROR: Constraint Exception: " + ErrorType.TITLE_OR_DIRECTOR_NOT_VALID.value() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ErrorType.TITLE_OR_DIRECTOR_NOT_VALID.value(), ex.getMessage());
        return new ResponseEntity<>(err, ErrorType.TITLE_OR_DIRECTOR_NOT_VALID.getStatus());
    }

    /* TODO documentazione*/
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error("===> ERROR: Constraint Exception: " + ErrorType.BODY_NOT_VALID.value() + " - " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(ErrorType.BODY_NOT_VALID.value(), ErrorType.BODY_NOT_VALID.getMessage());
        return new ResponseEntity<>(err, ErrorType.BODY_NOT_VALID.getStatus());
    }

    //TODO documentazione
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //TODO logger
        String parameter = ex.getParameterName();
        ErrorResponse err = new ErrorResponse(ErrorType.MISSING_PARAM.value(), ErrorType.MISSING_PARAM.getMessage() + parameter);
        super.handleMissingServletRequestParameter(ex, headers, status, request);
        return new ResponseEntity<>(err, ErrorType.MISSING_PARAM.getStatus());
    }

}
