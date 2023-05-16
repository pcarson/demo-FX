package com.example.demofx.exception;

public class QueryFailureException extends Exception {

    public QueryFailureException(String message) {
        super(message);
    }

    public QueryFailureException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
