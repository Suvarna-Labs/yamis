package org.suvarna.yamis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.suvarna.yamis.dto.request.AssignGroupsRequest;
import org.suvarna.yamis.dto.request.CreateUserRequest;
import org.suvarna.yamis.dto.request.UpdateUserRequest;
import org.suvarna.yamis.dto.response.UserResponse;

import java.util.Set;
import java.util.UUID;

public interface UserService {

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUserByUid(UUID uid);

    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(UUID uid, UpdateUserRequest request);

    void deleteUser(UUID uid);

    UserResponse assignGroups(UUID uid, AssignGroupsRequest request);

    UserResponse removeGroup(UUID uid, UUID groupId);

    Set<String> getUserEffectivePrivileges(UUID uid);
}

