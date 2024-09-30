    package org.example.msauthservice.service;

    import org.example.msauthservice.model.entity.User;
    import org.example.msauthservice.model.entity.Role; // Importa la entidad Role
    import org.example.msauthservice.repository.UserRepository;
    import org.example.msauthservice.repository.RoleRepository; // Importa el repositorio de roles
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.Optional;

    @Service
    public class AuthService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoleRepository roleRepository; // Inyección del RoleRepository

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

            // Obtener el rol de la base de datos
            // Obtener el rol de la base de datos
            Role userRole = roleRepository.findByName("ROLE_USER"); // O el rol que desees asignar
            if (userRole != null) {
                user.getRoles().add(userRole); // Asignar el rol al usuario
            } else {
                throw new RuntimeException("Role not found: ROLE_USER");
            }

            // Guardar el nuevo usuario en la base de datos y devolverlo envuelto en Optional
            return Optional.of(userRepository.save(user));
        }
        // Método para asignar un rol a un usuario
        public void assignRoleToUser(Long userId, Long roleId) {
            Optional<User> user = userRepository.findById(userId);
            Optional<Role> role = roleRepository.findById(roleId);

            if (user.isPresent() && role.isPresent()) {
                user.get().getRoles().add(role.get());
                userRepository.save(user.get());
            }
        }
    }
