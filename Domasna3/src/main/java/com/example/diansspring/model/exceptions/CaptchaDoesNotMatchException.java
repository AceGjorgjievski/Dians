package com.example.diansspring.model.exceptions;

public class CaptchaDoesNotMatchException extends RuntimeException{
    public CaptchaDoesNotMatchException() {
        super("Invalid Captcha");
    }
}
