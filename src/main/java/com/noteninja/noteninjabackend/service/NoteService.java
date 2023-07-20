package com.noteninja.noteninjabackend.service;

import com.noteninja.noteninjabackend.model.request.SaveNoteRequest;
import com.noteninja.noteninjabackend.model.response.NoteCardResponse;
import com.noteninja.noteninjabackend.model.response.SavedNoteResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface NoteService {

    SavedNoteResponse saveNote(SaveNoteRequest saveNoteRequest);

    Iterable<NoteCardResponse> getNotes(int page);

    void deleteNote(UUID id);
}
