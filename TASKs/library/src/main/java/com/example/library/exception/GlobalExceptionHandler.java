package com.example.library.exception;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorDeleteException.class)
    public ResponseEntity<String> handleAuthorDeleteException(
            AuthorDeleteException ex) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BookNotPurchasedException.class)
    public ResponseEntity<String> handleBookNotPurchasedException(
            BookNotPurchasedException ex) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    // Optional: handle generic exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(
            RuntimeException ex) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}