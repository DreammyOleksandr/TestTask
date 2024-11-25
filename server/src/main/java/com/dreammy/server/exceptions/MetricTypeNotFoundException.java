package com.dreammy.server.exceptions;

public class MetricTypeNotFoundException extends RuntimeException {
    public MetricTypeNotFoundException(String message) {
        super(message);
    }
}
