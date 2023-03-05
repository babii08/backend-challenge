package com.mvpmatch.backend.application.domain.exceptions;

import lombok.extern.slf4j.Slf4j;

public class InvalidRoleException extends RuntimeException {

    public InvalidRoleException(final String message) {

        super(message);
    }
}
