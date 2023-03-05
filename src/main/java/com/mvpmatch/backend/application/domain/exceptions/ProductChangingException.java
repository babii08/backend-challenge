package com.mvpmatch.backend.application.domain.exceptions;

public class ProductChangingException extends RuntimeException {

    public ProductChangingException(final String message) {
        super(message);
    }
}
