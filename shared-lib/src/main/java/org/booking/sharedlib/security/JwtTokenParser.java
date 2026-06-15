package org.booking.sharedlib.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.booking.sharedlib.security.dto.JwtUserInfo;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class JwtTokenParser {

    private final SecretKey signingKey;

    public JwtTokenParser(String base64Secret) {
        byte[] keyBytes = Base64.getDecoder().decode(base64Secret.trim());
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtUserInfo parse(String token) {
        Claims claims = extractClaims(token);
        return new JwtUserInfo(
                claims.get("userId", String.class),
                claims.getSubject(),
                claims.get("role", String.class)
        );
    }

    public boolean isValid(String token) {
        try {
            return extractClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            log.warn("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }


    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}