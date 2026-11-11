package org.suvarna.yamis.dto.response;

import java.util.Set;
import java.util.UUID;

public record GroupResponse(
        UUID groupId,
        String groupName,
        boolean isSuperAdmin,
        Set<String> roleNames
) {}

