package com.mvpmatch.backend.application.domain.exceptions;

public class InvalidCostException extends RuntimeException {

    public InvalidCostException(String message) {
        super(message);
    }
}
