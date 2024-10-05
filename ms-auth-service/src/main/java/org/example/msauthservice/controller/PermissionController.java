package org.example.msauthservice.controller;

import org.example.msauthservice.model.dto.PermissionDto;
import org.example.msauthservice.model.entity.Permission;
import org.example.msauthservice.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // Solo los usuarios con el permiso ADMIN_PRIVILEGES pueden crear nuevos permisos
    //@PreAuthorize("hasAuthority('ADMIN_PRIVILEGES')")
    @PostMapping("/create")
    public ResponseEntity<Permission> createPermission(@RequestBody PermissionDto permissionDto) {
        Permission permission = permissionService.createPermission(permissionDto);
        return ResponseEntity.ok(permission);
    }

    // Solo los usuarios con el permiso READ_PRIVILEGES pueden ver la lista de permisos
    @PreAuthorize("hasAuthority('READ_PRIVILEGES')")
    @GetMapping("/all")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }
}
