package com.example.diansspring.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException{
    public InvalidUserCredentialsException() {
        super(String.format("The provided credentials are not valid"));
    }
}
