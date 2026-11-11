package org.suvarna.yamis.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.suvarna.yamis.dto.request.AssignGroupsRequest;
import org.suvarna.yamis.dto.request.CreateUserRequest;
import org.suvarna.yamis.dto.request.UpdateUserRequest;
import org.suvarna.yamis.dto.response.UserResponse;
import org.suvarna.yamis.service.UserService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<UserResponse> getUserByUid(@PathVariable UUID uid) {
        return ResponseEntity.ok(userService.getUserByUid(uid));
    }

    @PutMapping("/{uid}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID uid,
            @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(uid, request));
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uid) {
        userService.deleteUser(uid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{uid}/groups")
    public ResponseEntity<UserResponse> assignGroups(
            @PathVariable UUID uid,
            @Valid @RequestBody AssignGroupsRequest request) {
        return ResponseEntity.ok(userService.assignGroups(uid, request));
    }

    @DeleteMapping("/{uid}/groups/{groupId}")
    public ResponseEntity<UserResponse> removeGroup(
            @PathVariable UUID uid,
            @PathVariable UUID groupId) {
        return ResponseEntity.ok(userService.removeGroup(uid, groupId));
    }

    @GetMapping("/{uid}/privileges")
    public ResponseEntity<Set<String>> getUserEffectivePrivileges(@PathVariable UUID uid) {
        return ResponseEntity.ok(userService.getUserEffectivePrivileges(uid));
    }
}

