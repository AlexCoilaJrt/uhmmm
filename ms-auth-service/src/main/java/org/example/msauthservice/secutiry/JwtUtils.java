package org.example.msauthservice.secutiry;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.msauthservice.service.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private String jwtSecret = "tuJwtSecret";  // Tu clave secreta para firmar el JWT
    private int jwtExpirationMs = 86400000;    // Tiempo de expiración del token

    // Método para generar el token JWT
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))   // Agrega el nombre del usuario al token
                .setIssuedAt(new Date())                     // Fecha de emisión
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))  // Fecha de expiración
                .signWith(SignatureAlgorithm.HS512, jwtSecret)  // Firmamos el token con HS512 y la clave secreta
                .compact();
    }

    // Validar el token
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            System.out.println("JWT inválido: " + e.getMessage());
        }

        return false;
    }

    // Obtener el nombre del usuario del token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}