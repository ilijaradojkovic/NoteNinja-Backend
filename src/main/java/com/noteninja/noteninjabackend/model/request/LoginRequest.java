package com.noteninja.noteninjabackend.model.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String email,

        @NotBlank
        String password
) {


}