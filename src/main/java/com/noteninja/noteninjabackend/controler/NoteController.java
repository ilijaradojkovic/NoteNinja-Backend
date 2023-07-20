package com.noteninja.noteninjabackend.controler;

import com.noteninja.noteninjabackend.model.request.SaveNoteRequest;
import com.noteninja.noteninjabackend.model.response.NoteCardResponse;
import com.noteninja.noteninjabackend.model.response.Response;
import com.noteninja.noteninjabackend.model.response.SavedNoteResponse;
import com.noteninja.noteninjabackend.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

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
    public Response getNotes(@RequestParam("page") int page){
        Iterable<NoteCardResponse> noteCardResponses=noteService.getNotes(page);

        return  Response.builder()
                .data(Map.of("notes",noteCardResponses))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

}
