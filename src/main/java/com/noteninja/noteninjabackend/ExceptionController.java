package com.noteninja.noteninjabackend;

import com.noteninja.noteninjabackend.exception.NoteNotFoundException;
import com.noteninja.noteninjabackend.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoteNotFoundException.class)
   public ResponseEntity<Response> notFoundNote(NoteNotFoundException exception){
        return  ResponseEntity.badRequest().body(Response.builder()
                .data(Map.of("note",exception.getId()))
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .developerMessage("Wrong note id was passed")
                .message("Note with id "+ exception.getId()+" was not found")
                .reason("Could not find note with id "+ exception.getId())
                .timestamp(LocalDateTime.now())
                .build());
    }

}
