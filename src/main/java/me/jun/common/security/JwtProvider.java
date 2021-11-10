package me.jun.common.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@PropertySource("classpath:application.properties")
public class JwtProvider {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final Long expiredTime;

    public JwtProvider(@Value("#{T(java.lang.Long).parseLong(${expired-time})}") Long expiredTime) {
        this.expiredTime = expiredTime;
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
