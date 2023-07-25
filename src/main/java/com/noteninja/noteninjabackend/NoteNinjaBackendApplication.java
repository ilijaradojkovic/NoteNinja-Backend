package com.noteninja.noteninjabackend;

import com.noteninja.noteninjabackend.model.ERole;
import com.noteninja.noteninjabackend.model.entity.Role;
import com.noteninja.noteninjabackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoteNinjaBackendApplication   {

    public static void main(String[] args) {
        SpringApplication.run(NoteNinjaBackendApplication.class, args);
    }



}
