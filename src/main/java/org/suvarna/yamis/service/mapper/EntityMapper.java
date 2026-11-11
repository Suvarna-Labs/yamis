package org.suvarna.yamis.service.mapper;

import org.suvarna.yamis.dto.response.GroupResponse;
import org.suvarna.yamis.dto.response.PrivilegeResponse;
import org.suvarna.yamis.dto.response.RoleResponse;
import org.suvarna.yamis.dto.response.UserResponse;
import org.suvarna.yamis.entity.Group;
import org.suvarna.yamis.entity.Privilege;
import org.suvarna.yamis.entity.Role;
import org.suvarna.yamis.entity.User;

import java.util.Collections;
import java.util.stream.Collectors;

public final class EntityMapper {

    private EntityMapper() {
    }

    public static UserResponse toUserResponse(User user) {
        if (user == null) return null;
        return new UserResponse(
                user.getUid(),
                user.getUsername(),
                user.getDisplayName(),
                user.getEmail(),
                user.isEnabled(),
                user.isActive(),
                user.getGroups() != null
                        ? user.getGroups().stream().map(Group::getGroupName).collect(Collectors.toSet())
                        : Collections.emptySet()
        );
    }

    public static GroupResponse toGroupResponse(Group group) {
        if (group == null) return null;
        return new GroupResponse(
                group.getGroupId(),
                group.getGroupName(),
                Boolean.TRUE.equals(group.getSuperAdmin()),
                group.getRoles() != null
                        ? group.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet())
                        : Collections.emptySet()
        );
    }

    public static RoleResponse toRoleResponse(Role role) {
        if (role == null) return null;
        return new RoleResponse(
                role.getRoleName(),
                role.getPrivileges() != null
                        ? role.getPrivileges().stream().map(Privilege::getPrivilegeName).collect(Collectors.toSet())
                        : Collections.emptySet()
        );
    }

    public static PrivilegeResponse toPrivilegeResponse(Privilege privilege) {
        if (privilege == null) return null;
        return new PrivilegeResponse(
                privilege.getPrivilegeName(),
                privilege.getDescription()
        );
    }
}

