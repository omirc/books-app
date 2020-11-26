package com.omirc.library.exceptions;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message, Throwable cause){
        super(message, cause);
    }
}
