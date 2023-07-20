package com.noteninja.noteninjabackend.model.entity;

import com.noteninja.noteninjabackend.model.NoteType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "note")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;
    private NoteType noteType;
    private LocalDateTime createdAt;
    private String password;
    private Boolean isLocked;


}
