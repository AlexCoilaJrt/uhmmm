package org.example.msauthservice.service;

import org.example.msauthservice.model.entity.Permission;
import org.example.msauthservice.model.entity.Role;
import org.example.msauthservice.repository.PermissionRepository;
import org.example.msauthservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public void assignPermissionToRole(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role no encontrado"));
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));

        role.getPermissions().add(permission);
        roleRepository.save(role);  // Guarda los cambios en la base de datos
    }
}
