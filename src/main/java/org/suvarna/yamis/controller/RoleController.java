package org.suvarna.yamis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.suvarna.yamis.dto.request.AssignPrivilegesRequest;
import org.suvarna.yamis.dto.request.CreateRoleRequest;
import org.suvarna.yamis.dto.response.RoleResponse;
import org.suvarna.yamis.service.RoleService;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Role Management", description = "APIs for managing security roles and binding privileges")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @Operation(summary = "List all roles", description = "Returns a paginated list of all defined roles in stable PagedModel format.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved roles page")
    })
    public ResponseEntity<PagedModel<RoleResponse>> getAllRoles(
            @ParameterObject
            @PageableDefault(page = 1, size = 10) Pageable pageable) {
        return ResponseEntity.ok(new PagedModel<>(roleService.getAllRoles(pageable)));
    }

    @PostMapping
    @Operation(summary = "Create role", description = "Creates a new role with optional initial privilege bindings.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Role successfully created"),
            @ApiResponse(responseCode = "400", description = "Validation error in request body"),
            @ApiResponse(responseCode = "409", description = "Role name already exists")
    })
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody CreateRoleRequest request) {
        RoleResponse response = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{roleName}")
    @Operation(summary = "Get role by name", description = "Retrieves role details including its assigned privileges.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role found and returned"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<RoleResponse> getRoleByName(
            @Parameter(description = "Unique name of the role (e.g. ROLE_ADMIN)", required = true)
            @PathVariable String roleName) {
        return ResponseEntity.ok(roleService.getRoleByName(roleName));
    }

    @DeleteMapping("/{roleName}")
    @Operation(summary = "Delete role", description = "Deletes a role by name.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Role successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<Void> deleteRole(
            @Parameter(description = "Unique name of the role to delete", required = true)
            @PathVariable String roleName) {
        roleService.deleteRole(roleName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{roleName}/privileges")
    @Operation(summary = "Assign privileges to role", description = "Associates one or more privileges with the specified role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Privileges successfully assigned to role"),
            @ApiResponse(responseCode = "400", description = "Invalid request or privilege names"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<RoleResponse> assignPrivileges(
            @Parameter(description = "Unique name of the role", required = true)
            @PathVariable String roleName,
            @Valid @RequestBody AssignPrivilegesRequest request) {
        return ResponseEntity.ok(roleService.assignPrivileges(roleName, request));
    }

    @DeleteMapping("/{roleName}/privileges/{privilegeName}")
    @Operation(summary = "Remove privilege from role", description = "Unassigns a privilege from the specified role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Privilege successfully removed from role"),
            @ApiResponse(responseCode = "404", description = "Role or privilege not found")
    })
    public ResponseEntity<RoleResponse> removePrivilege(
            @Parameter(description = "Unique name of the role", required = true)
            @PathVariable String roleName,
            @Parameter(description = "Unique name of the privilege to remove", required = true)
            @PathVariable String privilegeName) {
        return ResponseEntity.ok(roleService.removePrivilege(roleName, privilegeName));
    }
}
