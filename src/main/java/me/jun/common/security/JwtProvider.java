package me.jun.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
@SuppressWarnings("deprecation")
public class JwtProvider {

    private final String secretKey;

    private final Long expiredTime;

    public JwtProvider(@Value("#{T(java.lang.Long).parseLong(${expired-time})}") Long expiredTime,
                       @Value("#{${jwt-key}}") String secretKey) {
        this.expiredTime = expiredTime;
        this.secretKey = secretKey;
    }

    public String createJwt(String subject) {
        Date startTime = new Date();
        Date endTime = new Date(startTime.getTime() + expiredTime);

        Claims claims = Jwts.claims()
                .setSubject(subject)
                .setIssuedAt(startTime)
                .setExpiration(endTime);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(HS256, secretKey)
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
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    private Jws<Claims> extractClaimsJws(String jwt) {
        return createParser()
                .parseClaimsJws(jwt);
    }

    private JwtParser createParser() {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
    }
}
