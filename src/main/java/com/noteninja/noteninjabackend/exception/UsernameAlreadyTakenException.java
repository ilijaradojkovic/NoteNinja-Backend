package com.noteninja.noteninjabackend.exception;

public class UsernameAlreadyTakenException extends  RuntimeException{
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
