package com.noteninja.noteninjabackend.repository;

import com.noteninja.noteninjabackend.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID>, QuerydslPredicateExecutor<Note> {

    Optional<Note> findByIdAndUser_id(UUID noteId, Long userId);
}
