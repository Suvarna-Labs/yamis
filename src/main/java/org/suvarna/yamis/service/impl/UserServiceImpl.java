package org.suvarna.yamis.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suvarna.yamis.dto.request.AssignGroupsRequest;
import org.suvarna.yamis.dto.request.CreateUserRequest;
import org.suvarna.yamis.dto.request.UpdatePasswordRequest;
import org.suvarna.yamis.dto.request.UpdateUserRequest;
import org.suvarna.yamis.dto.response.UserResponse;
import org.suvarna.yamis.entity.Group;
import org.suvarna.yamis.entity.Privilege;
import org.suvarna.yamis.entity.Role;
import org.suvarna.yamis.entity.User;
import org.suvarna.yamis.exception.ConflictException;
import org.suvarna.yamis.exception.ResourceNotFoundException;
import org.suvarna.yamis.repository.GroupRepository;
import org.suvarna.yamis.repository.UserRepository;
import org.suvarna.yamis.service.UserService;
import org.suvarna.yamis.service.mapper.EntityMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public UserServiceImpl(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(EntityMapper::toUserResponse);
    }

    @Override
    public UserResponse getUserByUid(UUID uid) {
        User user = userRepository.findWithGroupsByUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UID: " + uid));
        return EntityMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ConflictException("Username '" + request.username() + "' is already in use.");
        }
        if (request.email() != null && !request.email().isBlank() && userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email '" + request.email() + "' is already in use.");
        }

        User user = new User(request.username(), request.displayName(), request.email());
        if (request.isEnabled() != null) {
            user.setEnabled(request.isEnabled());
        }
        if (request.isActive() != null) {
            user.setActive(request.isActive());
        }

        User saved = userRepository.save(user);
        return EntityMapper.toUserResponse(saved);
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID uid, UpdateUserRequest request) {
        User user = userRepository.findWithGroupsByUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UID: " + uid));

        if (request.email() != null && !request.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.email())) {
                throw new ConflictException("Email '" + request.email() + "' is already in use.");
            }
            user.setEmail(request.email());
        }

        if (request.displayName() != null) {
            user.setDisplayName(request.displayName());
        }
        if (request.isEnabled() != null) {
            user.setEnabled(request.isEnabled());
        }
        if (request.isActive() != null) {
            user.setActive(request.isActive());
        }

        User updated = userRepository.save(user);
        return EntityMapper.toUserResponse(updated);
    }

    @Override
    @Transactional
    public void updatePassword(UUID uid, UpdatePasswordRequest request) {
        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UID: " + uid));

        user.setPassword(request.newPassword());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID uid) {
        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UID: " + uid));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserResponse assignGroups(UUID uid, AssignGroupsRequest request) {
        User user = userRepository.findWithGroupsByUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UID: " + uid));

        List<Group> groups = groupRepository.findByGroupIdIn(request.groupIds());
        for (Group group : groups) {
            user.addGroup(group);
        }

        User saved = userRepository.save(user);
        return EntityMapper.toUserResponse(saved);
    }

    @Override
    @Transactional
    public UserResponse removeGroup(UUID uid, UUID groupId) {
        User user = userRepository.findWithGroupsByUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UID: " + uid));

        Group group = groupRepository.findByGroupId(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with groupId: " + groupId));

        user.removeGroup(group);
        User saved = userRepository.save(user);
        return EntityMapper.toUserResponse(saved);
    }

    @Override
    public Set<String> getUserEffectivePrivileges(UUID uid) {
        User user = userRepository.findWithFullAuthoritiesByUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UID: " + uid));

        Set<String> privileges = new HashSet<>();
        if (user.getGroups() != null) {
            for (Group group : user.getGroups()) {
                if (group.getRoles() != null) {
                    for (Role role : group.getRoles()) {
                        if (role.getPrivileges() != null) {
                            for (Privilege privilege : role.getPrivileges()) {
                                privileges.add(privilege.getPrivilegeName());
                            }
                        }
                    }
                }
            }
        }
        return privileges;
    }
}
