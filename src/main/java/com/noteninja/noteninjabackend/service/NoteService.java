package com.noteninja.noteninjabackend.service;

import com.noteninja.noteninjabackend.exception.NoteNotFoundException;
import com.noteninja.noteninjabackend.model.NoteType;
import com.noteninja.noteninjabackend.model.request.SaveNoteRequest;
import com.noteninja.noteninjabackend.model.request.UpdateNoteRequest;
import com.noteninja.noteninjabackend.model.response.NoteCardResponse;
import com.noteninja.noteninjabackend.model.response.NoteDetails;
import com.noteninja.noteninjabackend.model.response.SavedNoteResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface NoteService {

    SavedNoteResponse saveNote(SaveNoteRequest saveNoteRequest);

    Iterable<NoteCardResponse> getNotes(int page, String search, NoteType noteType);

    void deleteNote(UUID id);

    void updateNote(UpdateNoteRequest updateNoteRequest, UUID id) throws NoteNotFoundException;
    NoteDetails getNoteDetails(UUID id) throws NoteNotFoundException ;
}
