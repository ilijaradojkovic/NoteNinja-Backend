package com.noteninja.noteninjabackend.service.impl;

import com.noteninja.noteninjabackend.mapper.NoteMapper;
import com.noteninja.noteninjabackend.model.entity.Note;
import com.noteninja.noteninjabackend.model.request.SaveNoteRequest;
import com.noteninja.noteninjabackend.model.response.SavedNoteResponse;
import com.noteninja.noteninjabackend.repository.NoteRepository;
import com.noteninja.noteninjabackend.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public SavedNoteResponse saveNote(SaveNoteRequest saveNoteRequest) {
        Note note = noteMapper.fromSaveNoteRequestToNote(saveNoteRequest);
        noteRepository.save(note);
        return noteMapper.fromNoteToSavedNoteResponse(note);
    }
}
