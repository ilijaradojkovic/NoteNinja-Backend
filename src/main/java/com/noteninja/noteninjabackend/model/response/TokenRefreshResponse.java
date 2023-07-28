package com.noteninja.noteninjabackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TokenRefreshResponse {
    private final String accessToken;
    private final String refreshToken;
    private String tokenType = "Bearer";
}
