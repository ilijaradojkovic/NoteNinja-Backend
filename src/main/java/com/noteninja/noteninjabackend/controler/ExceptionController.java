package com.noteninja.noteninjabackend.controler;

import com.noteninja.noteninjabackend.exception.*;
import com.noteninja.noteninjabackend.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Response> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(
                Response.builder()
                        .statusCode(HttpStatus.FORBIDDEN.value())
                        .timestamp(LocalDateTime.now())
                        .developerMessage("")
                        .reason("")
                        .status(HttpStatus.FORBIDDEN)
                        .build()
        );
    }

    @ExceptionHandler({EmailAlreadyTaken.class, UsernameAlreadyTakenException.class, WrontPasswordForNoteException.class})
    public ResponseEntity<Response> handle(Exception exception) {
        return ResponseEntity.badRequest().body(
                Response.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .developerMessage("")
                        .reason(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<Response> notFoundNote(NoteNotFoundException exception) {
        return ResponseEntity.badRequest().body(Response.builder()
                .data(Map.of("note", exception.getId()))
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .developerMessage("Wrong note id was passed")
                .message("Note with id " + exception.getId() + " was not found")
                .reason("Could not find note with id " + exception.getId())
                .timestamp(LocalDateTime.now())
                .build());
    }

}
