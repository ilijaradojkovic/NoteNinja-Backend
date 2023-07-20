package com.noteninja.noteninjabackend.mapper;

import com.noteninja.noteninjabackend.model.entity.Note;
import com.noteninja.noteninjabackend.model.request.SaveNoteRequest;
import com.noteninja.noteninjabackend.model.response.NoteCardResponse;
import com.noteninja.noteninjabackend.model.response.SavedNoteResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(
        imports = {UUID.class},
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface NoteMapper {

    @Mapping(target = "id",expression = "java(UUID.randomUUID())")
    @Mapping(target = "createdAt",expression = "java(LocalDateTime.now())")
    Note fromSaveNoteRequestToNote(SaveNoteRequest saveNoteRequest);

    SavedNoteResponse fromNoteToSavedNoteResponse(Note note);

    Iterable<NoteCardResponse> fromListNotesTo_ListNoteCardResponse(Iterable<Note> notesFound);



    NoteCardResponse fromNoteToNoteCardResponse(Note note);
}
