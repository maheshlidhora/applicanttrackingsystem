package com.newrise.applicanttrackingsystem.utils;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j2;
@ControllerAdvice // This annotation makes this class a global exception handler
@Log4j2
public class GlobalExceptionHandler 
{
	private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);
	// This method handles exceptions when @Valid validation fails
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
         log.error("Validation errors: " + errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
    
    // You can also handle other general RuntimeException or custom exceptions here
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGenericRuntimeException(RuntimeException ex) {
        log.error("An unexpected error occurred... : RuntimeException " + ex.getMessage(), ex);
        return new ResponseEntity<>("An unexpected error occurred ...: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
    }

    // You can add a generic handler for all other general exceptions as well
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllOtherExceptions(Exception ex) {
        log.error("An unhandled error occurred: Exception " + ex.getMessage(), ex);
        return new ResponseEntity<>("An unhandled error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
    }
}
