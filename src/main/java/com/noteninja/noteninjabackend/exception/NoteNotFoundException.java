package com.noteninja.noteninjabackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class NoteNotFoundException extends Exception{

    private UUID id;

}
