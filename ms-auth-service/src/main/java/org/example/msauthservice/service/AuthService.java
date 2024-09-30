package org.example.msauthservice.service;

import org.example.msauthservice.model.entity.User;
import org.example.msauthservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para registrar usuarios y devolver un Optional<User>
    public Optional<User> registerUser(String username, String password, String email) {
        // Verificar si el nombre de usuario ya existe
        if (userRepository.findByUsername(username).isPresent()) {
            return Optional.empty(); // Si el usuario ya existe, devolver Optional vacío
        }

        // Crear un nuevo usuario y encriptar la contraseña
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);

        // Guardar el nuevo usuario en la base de datos y devolverlo envuelto en Optional
        return Optional.of(userRepository.save(user));
    }
}
