package org.suvarna.yamis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.suvarna.yamis.dto.request.AssignGroupsRequest;
import org.suvarna.yamis.dto.request.CreateUserRequest;
import org.suvarna.yamis.dto.request.UpdatePasswordRequest;
import org.suvarna.yamis.dto.request.UpdateUserRequest;
import org.suvarna.yamis.dto.response.UserResponse;
import org.suvarna.yamis.service.UserService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "APIs for managing users, passwords, memberships, and resolving effective privileges")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "List all users", description = "Returns a paginated list of registered users.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users page")
    })
    public ResponseEntity<Page<UserResponse>> getAllUsers(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user record with a uniquely assigned UID.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Validation error in request body"),
            @ApiResponse(responseCode = "409", description = "Username or email is already taken")
    })
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{uid}")
    @Operation(summary = "Get user by UID", description = "Retrieves full user details by their unique UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found and returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> getUserByUid(
            @Parameter(description = "Unique identifier of the user (UUID)", required = true)
            @PathVariable UUID uid) {
        return ResponseEntity.ok(userService.getUserByUid(uid));
    }

    @PutMapping("/{uid}")
    @Operation(summary = "Update user", description = "Updates details of an existing user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error in request body"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Updated email conflict")
    })
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "Unique identifier of the user (UUID)", required = true)
            @PathVariable UUID uid,
            @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(uid, request));
    }

    @PutMapping("/{uid}/password")
    @Operation(summary = "Update user password", description = "Updates the password for the specified user.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Password successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error in request body"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> updatePassword(
            @Parameter(description = "Unique identifier of the user (UUID)", required = true)
            @PathVariable UUID uid,
            @Valid @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(uid, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{uid}")
    @Operation(summary = "Delete user", description = "Removes a user and their group memberships.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "Unique identifier of the user (UUID)", required = true)
            @PathVariable UUID uid) {
        userService.deleteUser(uid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{uid}/groups")
    @Operation(summary = "Assign groups to user", description = "Associates one or more groups with the specified user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Groups successfully assigned"),
            @ApiResponse(responseCode = "400", description = "Invalid request or group IDs"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> assignGroups(
            @Parameter(description = "Unique identifier of the user (UUID)", required = true)
            @PathVariable UUID uid,
            @Valid @RequestBody AssignGroupsRequest request) {
        return ResponseEntity.ok(userService.assignGroups(uid, request));
    }

    @DeleteMapping("/{uid}/groups/{groupId}")
    @Operation(summary = "Remove group from user", description = "Removes a user from a specific group.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group successfully unassigned from user"),
            @ApiResponse(responseCode = "404", description = "User or group not found")
    })
    public ResponseEntity<UserResponse> removeGroup(
            @Parameter(description = "Unique identifier of the user (UUID)", required = true)
            @PathVariable UUID uid,
            @Parameter(description = "Unique identifier of the group (UUID)", required = true)
            @PathVariable UUID groupId) {
        return ResponseEntity.ok(userService.removeGroup(uid, groupId));
    }

    @GetMapping("/{uid}/privileges")
    @Operation(summary = "Get user effective privileges", description = "Resolves and returns all privileges granted to the user across all their assigned groups and roles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully resolved privileges set"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Set<String>> getUserEffectivePrivileges(
            @Parameter(description = "Unique identifier of the user (UUID)", required = true)
            @PathVariable UUID uid) {
        return ResponseEntity.ok(userService.getUserEffectivePrivileges(uid));
    }
}
