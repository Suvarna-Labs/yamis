package org.suvarna.yamis.dto.response;

import java.util.Set;
import java.util.UUID;

public record UserResponse(
        UUID uid,
        String username,
        String displayName,
        String email,
        boolean isEnabled,
        boolean isActive,
        Set<String> groupNames
) {}

