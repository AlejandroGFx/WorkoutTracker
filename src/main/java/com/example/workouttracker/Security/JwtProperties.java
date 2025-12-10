package com.example.workouttracker.Security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
    private String secret;
    private Long expirationMs;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpirationMs() {
        return expirationMs;
    }

    public void setExpirationMs(Long expirationMs) {
        this.expirationMs = expirationMs;
    }
}
