package com.example.ibanvalidator.demo;

public class InvalidIBANCodeException extends RuntimeException {
    static final long serialVersionUID = -7144897190745766939L;

    public InvalidIBANCodeException(String message) {
        super(message);
    }
}
