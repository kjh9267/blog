package me.jun.guestbook.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private static final Long THIRTY_MINUTE = 1000 * 60 * 30L;
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String createJwt(String subject) {
        Date startTime = new Date();
        Date expiredTime = new Date(startTime.getTime() + THIRTY_MINUTE);

        Claims claims = Jwts.claims()
                .setSubject(subject)
                .setIssuedAt(startTime)
                .setExpiration(expiredTime);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractSubject(String jwt) {
        return extractClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    public void validateToken(String jwt) {
        try {
            extractClaimsJws(jwt);
        }
        catch (Exception e) {
            throw new InvalidTokenException("invalid token");
        }
    }

    private Jws<Claims> extractClaimsJws(String jwt) {
        return createParser()
                .parseClaimsJws(jwt);
    }

    private JwtParser createParser() {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build();
    }
}
