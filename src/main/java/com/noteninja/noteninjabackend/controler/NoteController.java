package com.noteninja.noteninjabackend.controler;

import com.noteninja.noteninjabackend.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
}
