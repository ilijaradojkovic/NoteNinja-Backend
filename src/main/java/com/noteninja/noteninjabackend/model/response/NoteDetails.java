package com.noteninja.noteninjabackend.model.response;

import com.noteninja.noteninjabackend.model.NoteType;

import java.time.LocalDateTime;
import java.util.UUID;

public record NoteDetails(
         UUID id,

         String title,
         String description,
         NoteType noteType,
         LocalDateTime createdAt,
         Boolean isLocked,
         Boolean isFavorite



) {
}
