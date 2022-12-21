package com.cfe.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class InvalidDataUpdateException extends RuntimeException {

    public InvalidDataUpdateException() {
        super(HttpStatus.PRECONDITION_FAILED.getReasonPhrase());
    }

    public InvalidDataUpdateException(String message) {
        super(message);
    }
}
