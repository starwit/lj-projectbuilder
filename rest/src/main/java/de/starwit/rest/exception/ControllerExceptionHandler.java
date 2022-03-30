package de.starwit.rest.exception;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import de.starwit.persistence.exception.NotificationException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.ClassUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "de.starwit.rest")
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleException(Exception ex) {
        LOG.error(ex.getClass() + " " + ex.getMessage(), ex.fillInStackTrace());
        NotificationDto output = new NotificationDto("error.internalServerError", "Internal Server Error");
        return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { InvalidDefinitionException.class })
    public ResponseEntity<Object> handleInvalidDefinitionException(Exception ex) {
        LOG.error(ex.getClass() + " " + ex.getMessage(), ex.fillInStackTrace());
        String outputString = "Invalid Definition: " + ex.getMessage() + ".";
        NotificationDto output = new NotificationDto("error.invalidDefinition", outputString);
        return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Unauthorized.class })
    public ResponseEntity<Object> handleUnauthorizedException(Unauthorized ex) {
        LOG.info("Unauthorized Exception: {}", ex.getMessage());
        NotificationDto output = new NotificationDto("error.unauthorized", "Unauthorized requests");
        return new ResponseEntity<>(output, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleException(MethodArgumentTypeMismatchException ex) {
        LOG.info("Wrong input value {}", ex.getMessage());
        Object exValue = ex.getValue();
        Class<?> exType = ex.getRequiredType();
        String className = exValue != null ? ClassUtils.getShortName(exValue.getClass()) : "";
        String reqType = exType != null ? ClassUtils.getShortName(exType) : "";
        String outputString =
            "Wrong input value " +
            ex.getValue() +
            ". Failed to convert value of type " +
            className +
            " to required type " +
            reqType +
            ".";
        NotificationDto output = new NotificationDto("error.wrongInputValue", outputString);
        return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NotificationException.class })
    public ResponseEntity<Object> handleException(NotificationException ex) {
        LOG.info("Wrong input value {}", ex.getMessage());
        NotificationDto output = new NotificationDto(ex.getExceptionKey(), ex.getExceptionMessage());
        return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { InvalidDataAccessApiUsageException.class })
    public ResponseEntity<Object> handleException(InvalidDataAccessApiUsageException ex) {
        LOG.info("{} Check if there is an ID declared while object shoud be created.", ex.getMessage());
        NotificationDto output = new NotificationDto(
            "error.badrequest",
            ex.getMessage() + " Check if there is an unvalid ID declared while object shoud be created."
        );
        return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<Object> handleException(EntityNotFoundException ex) {
        LOG.info("Entity not found Exception: {}", ex.getMessage());
        NotificationDto output = new NotificationDto("error.notfound", "Entity not found");
        return new ResponseEntity<>(output, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleException(EmptyResultDataAccessException ex) {
        LOG.info(ex.getMessage());
        NotificationDto output = new NotificationDto("error.notexists", "Does not exists and cannot be deleted.");
        return new ResponseEntity<>(output, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<Object> handleException(AccessDeniedException ex) {
        LOG.info(ex.getMessage());
        NotificationDto output = new NotificationDto("error.accessdenied", "access denied");
        output.setMessageKey("error.accessdenied");
        return new ResponseEntity<>(output, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex
            .getBindingResult()
            .getAllErrors()
            .forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
