package com.task.tracker.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private Date expiresAt;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken, Date expiresAt) {
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
    }
}
