package org.suvarna.yamis.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suvarna.yamis.dto.request.AssignRolesRequest;
import org.suvarna.yamis.dto.request.CreateGroupRequest;
import org.suvarna.yamis.dto.request.UpdateGroupRequest;
import org.suvarna.yamis.dto.response.GroupResponse;
import org.suvarna.yamis.entity.Group;
import org.suvarna.yamis.entity.Role;
import org.suvarna.yamis.entity.User;
import org.suvarna.yamis.exception.ConflictException;
import org.suvarna.yamis.exception.ResourceNotFoundException;
import org.suvarna.yamis.repository.GroupRepository;
import org.suvarna.yamis.repository.RoleRepository;
import org.suvarna.yamis.repository.UserRepository;
import org.suvarna.yamis.service.GroupService;
import org.suvarna.yamis.service.mapper.EntityMapper;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public GroupServiceImpl(GroupRepository groupRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<GroupResponse> getAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable).map(EntityMapper::toGroupResponse);
    }

    @Override
    public GroupResponse getGroupByGroupId(UUID groupId) {
        Group group = groupRepository.findWithRolesByGroupId(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with groupId: " + groupId));
        return EntityMapper.toGroupResponse(group);
    }

    @Override
    @Transactional
    public GroupResponse createGroup(CreateGroupRequest request) {
        if (groupRepository.existsByGroupName(request.groupName())) {
            throw new ConflictException("Group with name '" + request.groupName() + "' already exists.");
        }

        Group group = new Group(request.groupName(), request.isSuperAdmin());
        Group saved = groupRepository.save(group);
        return EntityMapper.toGroupResponse(saved);
    }

    @Override
    @Transactional
    public GroupResponse updateGroup(UUID groupId, UpdateGroupRequest request) {
        Group group = groupRepository.findWithRolesByGroupId(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with groupId: " + groupId));

        if (!group.getGroupName().equals(request.groupName()) && groupRepository.existsByGroupName(request.groupName())) {
            throw new ConflictException("Group with name '" + request.groupName() + "' already exists.");
        }

        group.setGroupName(request.groupName());
        if (request.isSuperAdmin() != null) {
            group.setSuperAdmin(request.isSuperAdmin());
        }

        Group updated = groupRepository.save(group);
        return EntityMapper.toGroupResponse(updated);
    }

    @Override
    @Transactional
    public void deleteGroup(UUID groupId) {
        Group group = groupRepository.findByGroupId(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with groupId: " + groupId));
        groupRepository.delete(group);
    }

    @Override
    @Transactional
    public GroupResponse assignRoles(UUID groupId, AssignRolesRequest request) {
        Group group = groupRepository.findWithRolesByGroupId(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with groupId: " + groupId));

        List<Role> roles = roleRepository.findByRoleNameIn(request.roleNames());
        for (Role role : roles) {
            group.addRole(role);
        }

        Group saved = groupRepository.save(group);
        return EntityMapper.toGroupResponse(saved);
    }

    @Override
    @Transactional
    public GroupResponse removeRole(UUID groupId, String roleName) {
        Group group = groupRepository.findWithRolesByGroupId(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with groupId: " + groupId));

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with roleName: " + roleName));

        group.removeRole(role);
        Group saved = groupRepository.save(group);
        return EntityMapper.toGroupResponse(saved);
    }

    @Override
    @Transactional
    public void addUserToGroup(UUID groupId, UUID userUid) {
        Group group = groupRepository.findByGroupId(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with groupId: " + groupId));

        User user = userRepository.findWithGroupsByUid(userUid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UID: " + userUid));

        user.addGroup(group);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeUserFromGroup(UUID groupId, UUID userUid) {
        Group group = groupRepository.findByGroupId(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with groupId: " + groupId));

        User user = userRepository.findWithGroupsByUid(userUid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UID: " + userUid));

        user.removeGroup(group);
        userRepository.save(user);
    }
}

