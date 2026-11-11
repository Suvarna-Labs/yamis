package org.suvarna.yamis.dto.response;

import java.util.Set;

public record RoleResponse(
        String roleName,
        Set<String> privilegeNames
) {}

