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
import org.suvarna.yamis.dto.request.AssignRolesRequest;
import org.suvarna.yamis.dto.request.CreateGroupRequest;
import org.suvarna.yamis.dto.request.UpdateGroupRequest;
import org.suvarna.yamis.dto.response.GroupResponse;
import org.suvarna.yamis.service.GroupService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups")
@Tag(name = "Group Management", description = "APIs for managing groups, assigning roles, and adding/removing user members")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    @Operation(summary = "List all groups", description = "Returns a paginated list of groups.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved groups page")
    })
    public ResponseEntity<Page<GroupResponse>> getAllGroups(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(groupService.getAllGroups(pageable));
    }

    @PostMapping
    @Operation(summary = "Create a new group", description = "Creates a new group with a unique name and auto-generated UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Group successfully created"),
            @ApiResponse(responseCode = "400", description = "Validation error in request body"),
            @ApiResponse(responseCode = "409", description = "Group name already exists")
    })
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody CreateGroupRequest request) {
        GroupResponse response = groupService.createGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{groupId}")
    @Operation(summary = "Get group by groupId", description = "Retrieves group details and assigned roles by group UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group found and returned"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    public ResponseEntity<GroupResponse> getGroupByGroupId(
            @Parameter(description = "Unique identifier of the group (UUID)", required = true)
            @PathVariable UUID groupId) {
        return ResponseEntity.ok(groupService.getGroupByGroupId(groupId));
    }

    @PutMapping("/{groupId}")
    @Operation(summary = "Update group", description = "Updates group name and super admin flag.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error in request body"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "409", description = "Updated group name conflict")
    })
    public ResponseEntity<GroupResponse> updateGroup(
            @Parameter(description = "Unique identifier of the group (UUID)", required = true)
            @PathVariable UUID groupId,
            @Valid @RequestBody UpdateGroupRequest request) {
        return ResponseEntity.ok(groupService.updateGroup(groupId, request));
    }

    @DeleteMapping("/{groupId}")
    @Operation(summary = "Delete group", description = "Deletes a group by its UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    public ResponseEntity<Void> deleteGroup(
            @Parameter(description = "Unique identifier of the group (UUID)", required = true)
            @PathVariable UUID groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{groupId}/roles")
    @Operation(summary = "Assign roles to group", description = "Assigns one or more roles to the specified group.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roles successfully assigned to group"),
            @ApiResponse(responseCode = "400", description = "Invalid request or role names"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    public ResponseEntity<GroupResponse> assignRoles(
            @Parameter(description = "Unique identifier of the group (UUID)", required = true)
            @PathVariable UUID groupId,
            @Valid @RequestBody AssignRolesRequest request) {
        return ResponseEntity.ok(groupService.assignRoles(groupId, request));
    }

    @DeleteMapping("/{groupId}/roles/{roleName}")
    @Operation(summary = "Remove role from group", description = "Unassigns a role from the specified group.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role successfully removed from group"),
            @ApiResponse(responseCode = "404", description = "Group or role not found")
    })
    public ResponseEntity<GroupResponse> removeRole(
            @Parameter(description = "Unique identifier of the group (UUID)", required = true)
            @PathVariable UUID groupId,
            @Parameter(description = "Name of the role to remove", required = true)
            @PathVariable String roleName) {
        return ResponseEntity.ok(groupService.removeRole(groupId, roleName));
    }

    @PostMapping("/{groupId}/users/{userUid}")
    @Operation(summary = "Add user to group", description = "Enrolls an existing user as a member of this group.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User successfully added to group"),
            @ApiResponse(responseCode = "404", description = "Group or user not found")
    })
    public ResponseEntity<Void> addUserToGroup(
            @Parameter(description = "Unique identifier of the group (UUID)", required = true)
            @PathVariable UUID groupId,
            @Parameter(description = "Unique identifier of the user (UUID)", required = true)
            @PathVariable UUID userUid) {
        groupService.addUserToGroup(groupId, userUid);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/users/{userUid}")
    @Operation(summary = "Remove user from group", description = "Removes a user's membership from this group.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User successfully removed from group"),
            @ApiResponse(responseCode = "404", description = "Group or user not found")
    })
    public ResponseEntity<Void> removeUserFromGroup(
            @Parameter(description = "Unique identifier of the group (UUID)", required = true)
            @PathVariable UUID groupId,
            @Parameter(description = "Unique identifier of the user (UUID)", required = true)
            @PathVariable UUID userUid) {
        groupService.removeUserFromGroup(groupId, userUid);
        return ResponseEntity.noContent().build();
    }
}
