package org.suvarna.yamis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Group details response")
public record GroupResponse(
        @Schema(description = "Public unique identifier (UUID)", example = "2ff36072-8f44-411f-9c65-f93b918d5c4c")
        UUID groupId,

        @Schema(description = "Unique group name", example = "YAMIS_SUPER_ADMIN")
        String groupName,

        @Schema(description = "Whether this group is a super admin group", example = "true")
        boolean isSuperAdmin,

        @Schema(description = "Set of assigned role names", example = "[\"ROLE_ADMIN\"]")
        Set<String> roleNames
) {}
