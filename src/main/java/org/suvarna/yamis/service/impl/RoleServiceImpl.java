package org.suvarna.yamis.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suvarna.yamis.dto.request.AssignPrivilegesRequest;
import org.suvarna.yamis.dto.request.CreateRoleRequest;
import org.suvarna.yamis.dto.response.RoleResponse;
import org.suvarna.yamis.entity.Privilege;
import org.suvarna.yamis.entity.Role;
import org.suvarna.yamis.exception.ConflictException;
import org.suvarna.yamis.exception.ResourceNotFoundException;
import org.suvarna.yamis.repository.PrivilegeRepository;
import org.suvarna.yamis.repository.RoleRepository;
import org.suvarna.yamis.service.RoleService;
import org.suvarna.yamis.service.mapper.EntityMapper;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Page<RoleResponse> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(EntityMapper::toRoleResponse);
    }

    @Override
    public RoleResponse getRoleByName(String roleName) {
        Role role = roleRepository.findWithPrivilegesByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with roleName: " + roleName));
        return EntityMapper.toRoleResponse(role);
    }

    @Override
    @Transactional
    public RoleResponse createRole(CreateRoleRequest request) {
        if (roleRepository.existsByRoleName(request.roleName())) {
            throw new ConflictException("Role with name '" + request.roleName() + "' already exists.");
        }

        Role role = new Role(request.roleName());
        if (request.privilegeNames() != null && !request.privilegeNames().isEmpty()) {
            List<Privilege> privileges = privilegeRepository.findByPrivilegeNameIn(request.privilegeNames());
            for (Privilege privilege : privileges) {
                role.addPrivilege(privilege);
            }
        }

        Role saved = roleRepository.save(role);
        return EntityMapper.toRoleResponse(saved);
    }

    @Override
    @Transactional
    public void deleteRole(String roleName) {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with roleName: " + roleName));
        roleRepository.delete(role);
    }

    @Override
    @Transactional
    public RoleResponse assignPrivileges(String roleName, AssignPrivilegesRequest request) {
        Role role = roleRepository.findWithPrivilegesByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with roleName: " + roleName));

        List<Privilege> privileges = privilegeRepository.findByPrivilegeNameIn(request.privilegeNames());
        for (Privilege privilege : privileges) {
            role.addPrivilege(privilege);
        }

        Role saved = roleRepository.save(role);
        return EntityMapper.toRoleResponse(saved);
    }

    @Override
    @Transactional
    public RoleResponse removePrivilege(String roleName, String privilegeName) {
        Role role = roleRepository.findWithPrivilegesByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with roleName: " + roleName));

        Privilege privilege = privilegeRepository.findByPrivilegeName(privilegeName)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with privilegeName: " + privilegeName));

        role.removePrivilege(privilege);
        Role saved = roleRepository.save(role);
        return EntityMapper.toRoleResponse(saved);
    }
}

