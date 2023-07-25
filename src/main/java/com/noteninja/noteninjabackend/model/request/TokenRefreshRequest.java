package com.noteninja.noteninjabackend.model.request;


import jakarta.validation.constraints.NotBlank;


public record TokenRefreshRequest(
        @NotBlank
        String refreshToken
) {
}