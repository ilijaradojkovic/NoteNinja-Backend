package com.noteninja.noteninjabackend.model.response;

import java.time.LocalDateTime;

public record SavedNoteResponse(
        String title,
        LocalDateTime createdAt) {


}
