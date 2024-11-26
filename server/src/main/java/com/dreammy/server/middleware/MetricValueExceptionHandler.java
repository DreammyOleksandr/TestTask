package com.dreammy.server.middleware;

import com.dreammy.server.exceptions.metricType.MetricTypeNotFoundException;
import com.dreammy.server.exceptions.metricValue.MetricValueNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class MetricValueExceptionHandler {

    @ExceptionHandler(MetricValueNotFoundException.class)
    public ResponseEntity<Object> handleMetricTypeNotFoundException(MetricValueNotFoundException ex) {
        return buildResponse("MetricValue is not found", ex.getMessage(), "MetricValue.NotFound", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponse(String message, String details, String code, HttpStatus status) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        response.put("details", details);
        response.put("code", code);
        return new ResponseEntity<>(response, status);
    }
}
