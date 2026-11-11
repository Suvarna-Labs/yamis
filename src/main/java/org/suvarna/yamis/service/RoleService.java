package org.suvarna.yamis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.suvarna.yamis.dto.request.AssignPrivilegesRequest;
import org.suvarna.yamis.dto.request.CreateRoleRequest;
import org.suvarna.yamis.dto.response.RoleResponse;

public interface RoleService {

    Page<RoleResponse> getAllRoles(Pageable pageable);

    RoleResponse getRoleByName(String roleName);

    RoleResponse createRole(CreateRoleRequest request);

    void deleteRole(String roleName);

    RoleResponse assignPrivileges(String roleName, AssignPrivilegesRequest request);

    RoleResponse removePrivilege(String roleName, String privilegeName);
}

