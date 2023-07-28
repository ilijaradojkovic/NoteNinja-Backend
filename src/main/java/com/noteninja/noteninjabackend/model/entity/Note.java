package com.noteninja.noteninjabackend.model.entity;

import com.noteninja.noteninjabackend.model.NoteType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private NoteType noteType;
    private LocalDateTime createdAt;
    private String password;
    private Boolean isLocked;
    private Boolean isFavorite;

    @ManyToOne
    private User user;


}
