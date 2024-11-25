package com.dreammy.server.middleware;

import com.dreammy.server.exceptions.MetricTypeNotFoundException;
import com.dreammy.server.exceptions.SeedingDefaultMetricTypesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MetricTypeNotFoundException.class)
    public ResponseEntity<Object> handleMetricTypeNotFoundException(MetricTypeNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("code", "MetricType.NotFound");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("code", "InvalidArgument");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Invalid parameter type: " + ex.getName());
        response.put("details", "Expected argument type: " + ex.getRequiredType());
        response.put("code", "InvalidArgumentType");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeedingDefaultMetricTypesException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(SeedingDefaultMetricTypesException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Invalid condition");
        response.put("details", "Expected condition: " + ex.getMessage());
        response.put("code", "InvalidCondition");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleUnexpectedException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "An unexpected error occurred!" + ex.getMessage());
        response.put("details", ex.toString());
        response.put("code", "UnexpectedError");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
