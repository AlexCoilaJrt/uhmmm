package org.example.msauthservice.service;

import org.example.msauthservice.model.dto.PermissionDto;
import org.example.msauthservice.model.entity.Permission;
import org.example.msauthservice.model.entity.Role;
import org.example.msauthservice.model.entity.RolePermission;
import org.example.msauthservice.repository.PermissionRepository;
import org.example.msauthservice.repository.RolePermissionRepository;
import org.example.msauthservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    // Método para crear un permiso
    public Permission createPermission(PermissionDto permissionDto) {
        Permission permission = new Permission();
        permission.setName(permissionDto.getName());
        permission.setDescription(permissionDto.getDescription());
        return permissionRepository.save(permission);
    }

    // Método para obtener todos los permisos
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    // Método para asignar permisos a roles
    public void assignPermissionToRole(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found"));

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);

        rolePermissionRepository.save(rolePermission);
    }
}
