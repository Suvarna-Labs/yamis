package org.suvarna.yamis.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.suvarna.yamis.dto.request.AssignPrivilegesRequest;
import org.suvarna.yamis.dto.request.CreateRoleRequest;
import org.suvarna.yamis.dto.response.RoleResponse;
import org.suvarna.yamis.service.RoleService;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Page<RoleResponse>> getAllRoles(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(roleService.getAllRoles(pageable));
    }

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody CreateRoleRequest request) {
        RoleResponse response = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{roleName}")
    public ResponseEntity<RoleResponse> getRoleByName(@PathVariable String roleName) {
        return ResponseEntity.ok(roleService.getRoleByName(roleName));
    }

    @DeleteMapping("/{roleName}")
    public ResponseEntity<Void> deleteRole(@PathVariable String roleName) {
        roleService.deleteRole(roleName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{roleName}/privileges")
    public ResponseEntity<RoleResponse> assignPrivileges(
            @PathVariable String roleName,
            @Valid @RequestBody AssignPrivilegesRequest request) {
        return ResponseEntity.ok(roleService.assignPrivileges(roleName, request));
    }

    @DeleteMapping("/{roleName}/privileges/{privilegeName}")
    public ResponseEntity<RoleResponse> removePrivilege(
            @PathVariable String roleName,
            @PathVariable String privilegeName) {
        return ResponseEntity.ok(roleService.removePrivilege(roleName, privilegeName));
    }
}

