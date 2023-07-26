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

    SavedNoteResponse saveNote(SaveNoteRequest saveNoteRequest, Long id);

    Iterable<NoteCardResponse> getNotes(int page, String search, NoteType noteType, int pageSize,Long email);

    void deleteNote(UUID id);

    void updateNote(UpdateNoteRequest updateNoteRequest, UUID id) throws NoteNotFoundException;
    NoteDetails getNoteDetails(UUID id) throws NoteNotFoundException ;

    Long getNotesCount(String search, NoteType noteType, Long id);

    void toggleToFavorites(UUID uuid, Long id,boolean favorite) throws NoteNotFoundException;
}
