package org.example.msauthservice.secutiry;

import io.jsonwebtoken.Jwts;
import org.example.msauthservice.service.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private int jwtExpirationMs = 86400000; // 24 horas

    // Generar un JWT sin firma
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .compact(); // Genera el JWT sin firmarlo
    }

    // Validar el token JWT (sin firma)
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().parseClaimsJws(authToken);  // Omitimos la validación de la firma
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Obtener el nombre de usuario del token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().parseClaimsJws(token).getBody().getSubject();
    }
}
