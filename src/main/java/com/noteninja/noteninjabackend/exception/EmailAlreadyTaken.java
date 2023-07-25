package com.noteninja.noteninjabackend.exception;

public class EmailAlreadyTaken extends RuntimeException{
    public EmailAlreadyTaken(String message) {
        super(message);
    }
}
