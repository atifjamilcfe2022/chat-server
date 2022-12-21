package com.cfe.chat.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {
        super("Data not found");
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
