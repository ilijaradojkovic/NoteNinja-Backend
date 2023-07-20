package com.noteninja.noteninjabackend.service;

import com.noteninja.noteninjabackend.model.request.SaveNoteRequest;
import com.noteninja.noteninjabackend.model.response.SavedNoteResponse;
import org.springframework.stereotype.Component;

@Component
public interface NoteService {

    SavedNoteResponse saveNote(SaveNoteRequest saveNoteRequest);
}
