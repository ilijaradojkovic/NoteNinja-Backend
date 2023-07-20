package com.noteninja.noteninjabackend.model.request;

import com.noteninja.noteninjabackend.model.NoteType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateNoteRequest(
        @NotEmpty
        String title,
        String description,
        NoteType noteType
){
}
