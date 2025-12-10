package com.example.workouttracker.DTO;

public class LoginResponseDTO {
    String token;
    Long expirationMs;

    public LoginResponseDTO(String token, Long expirationMs) {
        this.token = token;
        this.expirationMs = expirationMs;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpirationMs() {
        return expirationMs;
    }

    public void setExpirationMs(Long expirationMs) {
        this.expirationMs = expirationMs;
    }
}
