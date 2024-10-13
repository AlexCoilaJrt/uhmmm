package org.example.msauthservice.controller;

import org.example.msauthservice.model.entity.Role;
import org.example.msauthservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/{roleId}/assign-permissions")
    public ResponseEntity<Role> assignPermissionsToRole(
            @PathVariable Long roleId,
            @RequestBody Map<String, List<Long>> permissionIdsMap) {

        // Extrae la lista de IDs de permisos del JSON
        List<Long> permissionIds = permissionIdsMap.get("permissionIds");

        // Pasa la lista convertida a HashSet al servicio
        return ResponseEntity.ok(roleService.assignPermissionsToRole(roleId, new HashSet<>(permissionIds)));
    }

}
