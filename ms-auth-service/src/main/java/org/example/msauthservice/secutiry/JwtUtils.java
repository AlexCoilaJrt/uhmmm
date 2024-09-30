package org.example.msauthservice.secutiry;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.msauthservice.service.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final Key jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Clave secreta
    private final int jwtExpirationMs = 86400000; // 24 horas

    // Generar un JWT con firma
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecret) // Usa la clave para firmar el token
                .compact();
    }

    // Validar el token JWT (con firma)
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken); // Verifica la firma
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Obtener el nombre de usuario del token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().getSubject();
    }
}
