package com.util.sample.library.exceptions;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message, Throwable cause){
        super(message, cause);
    }
}