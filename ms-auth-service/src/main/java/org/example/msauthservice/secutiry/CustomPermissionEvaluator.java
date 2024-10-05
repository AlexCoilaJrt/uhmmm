package org.example.msauthservice.secutiry;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || permission == null) {
            return false;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Extraer los permisos del usuario (considerando que las autoridades son los permisos)
        Set<String> userPermissions = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toSet());

        // Verificar si el usuario tiene el permiso requerido
        return userPermissions.contains(permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        // Este método se usa para verificar permisos a nivel de objetos específicos
        return false;
    }
}
