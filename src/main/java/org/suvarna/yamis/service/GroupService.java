package org.suvarna.yamis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.suvarna.yamis.dto.request.AssignRolesRequest;
import org.suvarna.yamis.dto.request.CreateGroupRequest;
import org.suvarna.yamis.dto.request.UpdateGroupRequest;
import org.suvarna.yamis.dto.response.GroupResponse;

import java.util.UUID;

public interface GroupService {

    Page<GroupResponse> getAllGroups(Pageable pageable);

    GroupResponse getGroupByGroupId(UUID groupId);

    GroupResponse createGroup(CreateGroupRequest request);

    GroupResponse updateGroup(UUID groupId, UpdateGroupRequest request);

    void deleteGroup(UUID groupId);

    GroupResponse assignRoles(UUID groupId, AssignRolesRequest request);

    GroupResponse removeRole(UUID groupId, String roleName);

    void addUserToGroup(UUID groupId, UUID userUid);

    void removeUserFromGroup(UUID groupId, UUID userUid);
}

