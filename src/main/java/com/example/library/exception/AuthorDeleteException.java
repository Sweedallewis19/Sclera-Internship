package com.example.library.exception;

public class AuthorDeleteException extends RuntimeException {

    public AuthorDeleteException(String message) {
        super(message);
    }
}