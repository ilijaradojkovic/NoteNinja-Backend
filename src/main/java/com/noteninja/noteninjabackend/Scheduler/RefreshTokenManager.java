package com.noteninja.noteninjabackend.Scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

public class RefreshTokenManager {

    @Value("${app.jwtRefreshExpirationMs}")
    private String refreshTOkenDuration;
    @Scheduled
    public void deleteAfterTime(){

    }
}
