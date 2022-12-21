package com.cfe.chat.exception;

import com.cfe.chat.exception.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleException(Exception exception) {
        log.error("Error Occurs: {}", exception.getMessage(), exception);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(ApiError.builder()
                        .errorCode(httpStatus.value())
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiError> handleDataNotFoundException(DataNotFoundException exception) {
        log.error("Error Occurs: {}", exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiError.builder()
                        .errorCode(HttpStatus.NOT_FOUND.value())
                        .message(exception.getMessage())
                        .description("")
                        .build()
        );
    }

}
