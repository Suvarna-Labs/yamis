package org.suvarna.yamis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.suvarna.yamis.dto.response.PrivilegeResponse;

public interface PrivilegeService {

    Page<PrivilegeResponse> getAllPrivileges(Pageable pageable);

    PrivilegeResponse getPrivilegeByName(String privilegeName);
}

