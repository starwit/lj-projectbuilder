package de.starwit.rest.exception;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import de.starwit.persistence.exception.NotificationException;

@ControllerAdvice(basePackages = "de.starwit.rest")
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleException(Exception ex) {
        LOG.error(ex.getClass() + " " + ex.getMessage(), ex.fillInStackTrace());
        return new ResponseEntity<Object>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { InvalidDefinitionException.class })
    public ResponseEntity<Object> handleInvalidDefinitionException(Exception ex) {
        LOG.error(ex.getClass() + " " + ex.getMessage(), ex.fillInStackTrace());
        String output = "Invalid Definition: " + ex.getMessage() + ".";
        return new ResponseEntity<Object>(output, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Unauthorized.class })
    public ResponseEntity<Object> handleUnauthorizedException(Unauthorized ex) {
        LOG.info("Unauthorized Exception: ", ex.getMessage());
        return new ResponseEntity<Object>("Unauthorized request", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleException(MethodArgumentTypeMismatchException ex) {
        LOG.info("Wrong input value ", ex.getMessage());
        String output = "Wrong input value " + ex.getValue() + ". Failed to convert value of type "
                + ClassUtils.getShortName(ex.getValue().getClass()) + " to required type "
                + ClassUtils.getShortName(ex.getRequiredType()) + ".";
        return new ResponseEntity<Object>(output, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NotificationException.class })
    public ResponseEntity<Object> handleException(NotificationException ex) {
        LOG.info("Wrong input value ", ex.getMessage());
        NotificationDto output = new NotificationDto();
        output.setMessageKey(ex.getExceptionKey());
        output.setMessage(ex.getExceptionMessage());
        return new ResponseEntity<Object>(output, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<Object> handleException(EntityNotFoundException ex) {
        LOG.info("Entity not found Exception: ", ex.getMessage());
        return new ResponseEntity<Object>("not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleException(EmptyResultDataAccessException ex) {
        LOG.info(ex.getMessage());
        return new ResponseEntity<Object>("Does not exists and cannot be deleted.", HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

}