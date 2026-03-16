package com.example.library.exception;

public class BookNotPurchasedException extends RuntimeException {

    public BookNotPurchasedException(String message) {
        super(message);
    }
}
