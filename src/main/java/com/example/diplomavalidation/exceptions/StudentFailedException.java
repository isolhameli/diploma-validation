package com.example.diplomavalidation.exceptions;

public class StudentFailedException extends RuntimeException{

    private String message;

    public StudentFailedException(String message) {
        super(message);
    }

}
