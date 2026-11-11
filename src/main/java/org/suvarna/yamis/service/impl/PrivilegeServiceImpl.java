package org.suvarna.yamis.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suvarna.yamis.dto.response.PrivilegeResponse;
import org.suvarna.yamis.entity.Privilege;
import org.suvarna.yamis.exception.ResourceNotFoundException;
import org.suvarna.yamis.repository.PrivilegeRepository;
import org.suvarna.yamis.service.PrivilegeService;
import org.suvarna.yamis.service.mapper.EntityMapper;

@Service
@Transactional(readOnly = true)
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Page<PrivilegeResponse> getAllPrivileges(Pageable pageable) {
        return privilegeRepository.findAll(pageable).map(EntityMapper::toPrivilegeResponse);
    }

    @Override
    public PrivilegeResponse getPrivilegeByName(String privilegeName) {
        Privilege privilege = privilegeRepository.findByPrivilegeName(privilegeName)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with name: " + privilegeName));
        return EntityMapper.toPrivilegeResponse(privilege);
    }
}

