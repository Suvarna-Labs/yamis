package org.suvarna.yamis.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.suvarna.yamis.dto.request.AssignRolesRequest;
import org.suvarna.yamis.dto.request.CreateGroupRequest;
import org.suvarna.yamis.dto.request.UpdateGroupRequest;
import org.suvarna.yamis.dto.response.GroupResponse;
import org.suvarna.yamis.service.GroupService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<Page<GroupResponse>> getAllGroups(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(groupService.getAllGroups(pageable));
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody CreateGroupRequest request) {
        GroupResponse response = groupService.createGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getGroupByGroupId(@PathVariable UUID groupId) {
        return ResponseEntity.ok(groupService.getGroupByGroupId(groupId));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupResponse> updateGroup(
            @PathVariable UUID groupId,
            @Valid @RequestBody UpdateGroupRequest request) {
        return ResponseEntity.ok(groupService.updateGroup(groupId, request));
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable UUID groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{groupId}/roles")
    public ResponseEntity<GroupResponse> assignRoles(
            @PathVariable UUID groupId,
            @Valid @RequestBody AssignRolesRequest request) {
        return ResponseEntity.ok(groupService.assignRoles(groupId, request));
    }

    @DeleteMapping("/{groupId}/roles/{roleName}")
    public ResponseEntity<GroupResponse> removeRole(
            @PathVariable UUID groupId,
            @PathVariable String roleName) {
        return ResponseEntity.ok(groupService.removeRole(groupId, roleName));
    }

    @PostMapping("/{groupId}/users/{userUid}")
    public ResponseEntity<Void> addUserToGroup(
            @PathVariable UUID groupId,
            @PathVariable UUID userUid) {
        groupService.addUserToGroup(groupId, userUid);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/users/{userUid}")
    public ResponseEntity<Void> removeUserFromGroup(
            @PathVariable UUID groupId,
            @PathVariable UUID userUid) {
        groupService.removeUserFromGroup(groupId, userUid);
        return ResponseEntity.noContent().build();
    }
}

