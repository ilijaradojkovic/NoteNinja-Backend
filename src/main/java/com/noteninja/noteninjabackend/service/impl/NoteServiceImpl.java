package com.noteninja.noteninjabackend.service.impl;

import com.noteninja.noteninjabackend.exception.NoteNotFoundException;
import com.noteninja.noteninjabackend.mapper.NoteMapper;
import com.noteninja.noteninjabackend.model.entity.Note;
import com.noteninja.noteninjabackend.model.entity.QNote;
import com.noteninja.noteninjabackend.model.request.SaveNoteRequest;
import com.noteninja.noteninjabackend.model.request.UpdateNoteRequest;
import com.noteninja.noteninjabackend.model.response.NoteCardResponse;
import com.noteninja.noteninjabackend.model.response.NoteDetails;
import com.noteninja.noteninjabackend.model.response.SavedNoteResponse;
import com.noteninja.noteninjabackend.repository.NoteRepository;
import com.noteninja.noteninjabackend.service.NoteService;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Value("${page_size}")
    private int page_size;

    @Override
    public SavedNoteResponse saveNote(SaveNoteRequest saveNoteRequest) {
        Note note = noteMapper.fromSaveNoteRequestToNote(saveNoteRequest);
        noteRepository.save(note);
        return noteMapper.fromNoteToSavedNoteResponse(note);
    }

    @Override
    public Iterable<NoteCardResponse> getNotes(int page,String search) {
        Iterable<Note> notesFound;
        if(search==null || search.trim().isEmpty()) {
            notesFound = noteRepository.findAll(PageRequest.of(page, page_size)).toList();
        }else{
            QNote qNote=QNote.note;
            BooleanExpression contains = qNote.title.contains(search);
            notesFound = noteRepository.findAll(contains);
        }

        return noteMapper.fromListNotesTo_ListNoteCardResponse(notesFound);
    }

    @Override
    public void deleteNote(UUID id) {
        noteRepository.deleteById(id);

    }

    @Override
    @CachePut(cacheNames = "updateNote",key = "${id}")
    public void updateNote(UpdateNoteRequest updateNoteRequest, UUID id) throws NoteNotFoundException {
        Note note = noteRepository.findById(id).orElseThrow(()-> new NoteNotFoundException(id));
        if(updateNoteRequest.noteType()!=null)
            note.setNoteType(updateNoteRequest.noteType());
        if(updateNoteRequest.description()!=null)
            note.setDescription(updateNoteRequest.description());
        if(updateNoteRequest.title()!=null)
            note.setTitle(updateNoteRequest.title());

        noteRepository.save(note);
    }

    @Override
    @Cacheable(key = "${id}",cacheNames = "getNoteDetails")
    public NoteDetails getNoteDetails(UUID id) throws NoteNotFoundException {

        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        return noteMapper.fromNoteToNoteDetails(note);

    }
}
