package com.example.workouttracker.Security;

import com.example.workouttracker.Entity.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;
    @Value("${app.jwt.expiration-ms}")
    private Long expirationMs;

    public Long getExpirationMs() {
        return expirationMs;
    }

    public void setExpirationMs(Long expirationMs) {
        this.expirationMs = expirationMs;
    }

    public String generateToken(User user) {
        try {
            Instant now = Instant.now();
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issueTime(Date.from(now))
                    .claim("id", user.getId())
                    .claim("firstname", user.getFirstName())
                    .claim("lastname", user.getLastName())
                    .claim("password", user.getPassword())
                    .claim("email", user.getEmail())
                    .claim("role", user.getRole().toString())
                    .expirationTime(Date.from(now.plusMillis(expirationMs)))
                    .build();
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
            SignedJWT jwt = new SignedJWT(header, claims);

            byte[] secretBytes = Base64.getDecoder().decode(secret);
            JWSSigner signer = new MACSigner(secretBytes);
            jwt.sign(signer);
            return jwt.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error generating JWT", e);
        }
    }
}
