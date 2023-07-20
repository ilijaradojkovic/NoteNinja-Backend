package com.noteninja.noteninjabackend.model.request;

import com.noteninja.noteninjabackend.model.NoteType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record SaveNoteRequest(
        @NotEmpty
        String title,
        @NotEmpty
        String description,
        @NotNull
        NoteType noteType,
        Boolean isLocked,
        String password
) {
}
