package com.noteninja.noteninjabackend.controler;

import com.noteninja.noteninjabackend.exception.NoteNotFoundException;
import com.noteninja.noteninjabackend.model.FilterNoteType;
import com.noteninja.noteninjabackend.model.request.PasswordValidation;
import com.noteninja.noteninjabackend.model.request.SaveNoteRequest;
import com.noteninja.noteninjabackend.model.request.UpdateNoteRequest;
import com.noteninja.noteninjabackend.model.response.NoteCardResponse;
import com.noteninja.noteninjabackend.model.response.NoteDetails;
import com.noteninja.noteninjabackend.model.response.Response;
import com.noteninja.noteninjabackend.model.response.SavedNoteResponse;
import com.noteninja.noteninjabackend.security.UserDetailsImpl;
import com.noteninja.noteninjabackend.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public Response saveNote(@RequestBody  @Valid  SaveNoteRequest saveNoteRequest,@AuthenticationPrincipal UserDetailsImpl userDetails){

        SavedNoteResponse savedNoteResponse = noteService.saveNote(saveNoteRequest,userDetails.getId());
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
            @RequestParam(value = "page_size",defaultValue = "9") int pageSize,
            @RequestParam(value = "search",required = false) String search,
            @RequestParam(value = "note_type",required = false,defaultValue ="ALL")String noteType,
            @AuthenticationPrincipal UserDetailsImpl userDetails

            ){
        Iterable<NoteCardResponse> noteCardResponses=noteService.getNotes(page,search, FilterNoteType.valueOf(noteType),pageSize,userDetails.getId());

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

    @PostMapping("/{id}")
    public Response noteDetails(@PathVariable("id") UUID id
            ,@RequestBody() PasswordValidation notePassword
    )
            throws Exception {
        NoteDetails noteDetails = noteService.getNoteDetails(id,notePassword.password());
        return  Response.builder()
                .data(Map.of("note",noteDetails))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }


    @GetMapping("/total")
    public Response getTotalNumberOfNotes(  @RequestParam(value = "search",required = false) String search,
                                            @RequestParam(value = "note_type",required = false,defaultValue ="ALL") String noteType,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails
                                            ){
        //ovde bi trebal oza usera
        Long number=noteService.getNotesCount(search,FilterNoteType.valueOf(noteType),userDetails.getId());
        return Response.builder()
                .data(Map.of("total",number))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @PatchMapping("/favorites/{id}")
    public Response addToFavorites(
            @PathVariable("id") UUID noteId,
            @RequestParam("favorite") boolean favorite,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws NoteNotFoundException {
        noteService.toggleToFavorites(noteId,userDetails.getId(),favorite);
        return Response.builder()
                .data(Map.of("note_id",noteId))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
