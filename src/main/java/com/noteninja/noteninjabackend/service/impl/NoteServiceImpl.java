package com.noteninja.noteninjabackend.service.impl;

import com.noteninja.noteninjabackend.exception.NoteNotFoundException;
import com.noteninja.noteninjabackend.exception.UserNotFoundException;
import com.noteninja.noteninjabackend.mapper.NoteMapper;
import com.noteninja.noteninjabackend.model.NoteType;
import com.noteninja.noteninjabackend.model.entity.Note;
import com.noteninja.noteninjabackend.model.entity.QNote;
import com.noteninja.noteninjabackend.model.entity.User;
import com.noteninja.noteninjabackend.model.request.SaveNoteRequest;
import com.noteninja.noteninjabackend.model.request.UpdateNoteRequest;
import com.noteninja.noteninjabackend.model.response.NoteCardResponse;
import com.noteninja.noteninjabackend.model.response.NoteDetails;
import com.noteninja.noteninjabackend.model.response.SavedNoteResponse;
import com.noteninja.noteninjabackend.repository.NoteRepository;
import com.noteninja.noteninjabackend.repository.UserRepository;
import com.noteninja.noteninjabackend.service.NoteService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteMapper noteMapper;



    @Override
    public SavedNoteResponse saveNote(SaveNoteRequest saveNoteRequest, Long id) {
        log.info("Saving note "+ saveNoteRequest +" for user "+id);
         User user=  userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with id "+id +" not found"));

        Note note = noteMapper.fromSaveNoteRequestToNote(saveNoteRequest,user);
        noteRepository.save(note);
        return noteMapper.fromNoteToSavedNoteResponse(note);
    }

    @Override
    public Iterable<NoteCardResponse> getNotes(int page, String search, NoteType noteType, int pageSize,Long id) {
        log.info("Fetching notes for page:"+ page+" for search:"+ search + " for note_type:"+noteType + " for user "+ id);
        Iterable<Note> notesFound;
        //tip: you should not extract whole user with his password here
//        User user=  userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User with email "+email +" not found"));
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QNote qNote=QNote.note;
        if(search!=null){
            BooleanExpression contains = qNote.title.contains(search);
            booleanBuilder.and(contains);
        }
        if(noteType!=NoteType.ALL){
            BooleanExpression eq = qNote.noteType.eq(noteType);
            booleanBuilder.and(eq);
        }
        booleanBuilder.and(qNote.user.id.eq(id));

        notesFound=noteRepository.findAll(booleanBuilder, PageRequest.of(page,pageSize));

        return noteMapper.fromListNotesTo_ListNoteCardResponse(notesFound);
    }

    @Override
    public void deleteNote(UUID id) {
        log.info("Deleting note "+ id);
        noteRepository.deleteById(id);

    }

    @Override
    @CachePut(cacheNames = "updateNote",key = "${id}")
    public void updateNote(UpdateNoteRequest updateNoteRequest, UUID id) throws NoteNotFoundException {
        log.info("Updating note "+ id +"values: "+updateNoteRequest);
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
        log.info("Fetching note details "+ id);
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        return noteMapper.fromNoteToNoteDetails(note);

    }

    //kesiraj ovopo user idu
    @Override
    public Long getNotesCount(String search, NoteType noteType, Long id) {
        log.info("Fetch notes count "+ search +" "+ noteType);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QNote qNote=QNote.note;
        if(search!=null){
            BooleanExpression contains = qNote.title.contains(search);
            booleanBuilder.and(contains);
        }
        if(noteType!=NoteType.ALL){
            BooleanExpression eq = qNote.noteType.eq(noteType);
            booleanBuilder.and(eq);
        }
        booleanBuilder.and(qNote.user.id.eq(id));
        return noteRepository.count(booleanBuilder);
    }

    @Override
    public void toggleToFavorites(UUID noteId, Long userId,boolean favorite) throws NoteNotFoundException {
        Note note = noteRepository.findByIdAndUser_id(noteId, userId).orElseThrow(()->new NoteNotFoundException(noteId));
        note.setIsFavorite(favorite);
        noteRepository.save(note);

    }
}
