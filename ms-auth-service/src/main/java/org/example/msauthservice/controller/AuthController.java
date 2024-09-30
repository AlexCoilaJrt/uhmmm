package org.example.msauthservice.controller;

import org.example.msauthservice.model.dto.LoginRequest;
import org.example.msauthservice.model.dto.JwtResponse;
import org.example.msauthservice.model.dto.RegisterRequest;
import org.example.msauthservice.secutiry.JwtUtils;
import org.example.msauthservice.service.AuthService;
import org.example.msauthservice.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            String jwtToken = jwtUtils.generateJwtToken(authentication);
            return new JwtResponse(jwtToken);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }

    // Endpoint para registrar usuarios
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        // Recibe el Optional<User> desde el servicio
        Optional<User> registeredUser = authService.registerUser(
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getEmail()
        );

        // Verifica si el usuario fue registrado
        if (registeredUser.isPresent()) {
            return "User registered successfully";
        } else {
            return "User registration failed: Username already exists";
        }
    }
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        boolean isValid = jwtUtils.validateJwtToken(token);
        if (isValid) {
            return ResponseEntity.ok("Token válido");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }

}
