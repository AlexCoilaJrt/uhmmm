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

    public Optional<User> registerUser(String username, String password, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            return Optional.empty();
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));  // Aquí encriptamos la contraseña
        user.setEmail(email);

        return Optional.of(userRepository.save(user));
    }
}