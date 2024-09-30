package org.example.msauthservice.controller;

import org.example.msauthservice.model.dto.LoginRequest;
import org.example.msauthservice.model.dto.JwtResponse;
import org.example.msauthservice.model.dto.RegisterRequest;
import org.example.msauthservice.model.dto.RoleAssignmentRequest;
import org.example.msauthservice.repository.UserRepository;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;  // Asegúrate de que JwtUtils esté anotado con @Component en el paquete de security

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        Optional<User> registeredUser = authService.registerUser(
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getEmail()
        );

        if (registeredUser.isPresent()) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed: Username already exists");
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
    @GetMapping("/users")
    public List<User> listUsers() {
        return userRepository.findAll(); // Devuelve la lista de usuarios
    }
    @PostMapping("/assign-role")
    public ResponseEntity<User> assignRole(@RequestBody RoleAssignmentRequest request) {
        // Asignar el rol al usuario
        authService.assignRoleToUser(request.getUserId(), request.getRoleId());

        // Obtener el usuario actualizado
        Optional<User> updatedUser = userRepository.findById(request.getUserId());

        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



}
