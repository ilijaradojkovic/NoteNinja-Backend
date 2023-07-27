package com.noteninja.noteninjabackend.exception;

public class WrontPasswordForNoteException extends RuntimeException{
    public WrontPasswordForNoteException(String message) {
        super(message);
    }
}
