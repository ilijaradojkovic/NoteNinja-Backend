package com.noteninja.noteninjabackend.repository;

import com.noteninja.noteninjabackend.model.ERole;
import com.noteninja.noteninjabackend.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
