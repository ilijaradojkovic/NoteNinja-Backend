package com.noteninja.noteninjabackend.controler;

import com.noteninja.noteninjabackend.exception.NoteNotFoundException;
import com.noteninja.noteninjabackend.model.NoteType;
import com.noteninja.noteninjabackend.model.entity.Note;
import com.noteninja.noteninjabackend.model.request.SaveNoteRequest;
import com.noteninja.noteninjabackend.model.request.UpdateNoteRequest;
import com.noteninja.noteninjabackend.model.response.NoteCardResponse;
import com.noteninja.noteninjabackend.model.response.NoteDetails;
import com.noteninja.noteninjabackend.model.response.Response;
import com.noteninja.noteninjabackend.model.response.SavedNoteResponse;
import com.noteninja.noteninjabackend.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public Response saveNote(@RequestBody  @Valid  SaveNoteRequest saveNoteRequest){
        SavedNoteResponse savedNoteResponse = noteService.saveNote(saveNoteRequest);
        return Response.builder()
                .data(Map.of("note",savedNoteResponse))
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .build();
    }
    @GetMapping
    public Response getNotes(
            @RequestParam("page") int page,
            @RequestParam(value = "page_size",defaultValue = "10") int pageSize,
            @RequestParam(value = "search",required = false) String search,
            @RequestParam(value = "note_type",required = false,defaultValue ="ALL")String noteType

            ){
        Iterable<NoteCardResponse> noteCardResponses=noteService.getNotes(page,search, NoteType.valueOf(noteType),pageSize);

        return  Response.builder()
                .data(Map.of("notes",noteCardResponses))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @DeleteMapping("/{id}")
    public Response deleteNote(@PathVariable("id") UUID id){

        noteService.deleteNote(id);
        return  Response.builder()
                .data(Map.of("note_deleted",id))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();

    }

    @PutMapping("/{id}")
    public Response updateNote(@RequestBody UpdateNoteRequest updateNoteRequest, @PathVariable("id") UUID id) throws NoteNotFoundException {
        noteService.updateNote(updateNoteRequest,id);
        return  Response.builder()
                .data(Map.of("note",updateNoteRequest))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/{id}")
    public Response noteDetails(@PathVariable("id") UUID id) throws NoteNotFoundException {
        NoteDetails noteDetails = noteService.getNoteDetails(id);
        return  Response.builder()
                .data(Map.of("note",noteDetails))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }


    @GetMapping("/total")
    public Response getTotalNumberOfNotes(  @RequestParam(value = "search",required = false) String search,
                                            @RequestParam(value = "note_type",required = false,defaultValue ="ALL") String noteType){
        //ovde bi trebal oza usera
        Long number=noteService.getNotesCount(search,NoteType.valueOf(noteType));
        return Response.builder()
                .data(Map.of("total",number))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
