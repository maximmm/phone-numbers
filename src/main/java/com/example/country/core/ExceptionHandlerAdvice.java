package com.example.country.core;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.ResponseEntity.badRequest;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception) {
        var message = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(joining());

        return asBadRequest(message, exception);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception exception) {
        return asBadRequest("Something went wrong", exception);
    }

    private ResponseEntity<?> asBadRequest(String bodyMessage, Exception exception) {
        log.error(exception.getMessage(), exception);
        return badRequest().body(bodyMessage);
    }
}
