package org.example.msauthservice.controller;

import org.example.msauthservice.model.dto.RolePermissionDto;
import org.example.msauthservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Asignar permiso a un rol
    @PostMapping("/assign-permission")
    public ResponseEntity<?> assignPermissionToRole(@RequestBody RolePermissionDto rolePermissionDto) {
        roleService.assignPermissionToRole(rolePermissionDto.getRoleId(), rolePermissionDto.getPermissionId());
        return ResponseEntity.ok("Permiso asignado correctamente");
    }
}
