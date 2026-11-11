package org.suvarna.yamis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

@Schema(description = "Role details response")
public record RoleResponse(
        @Schema(description = "Unique role name", example = "ROLE_ADMIN")
        String roleName,

        @Schema(description = "Set of assigned privilege names", example = "[\"USER_CREATE\", \"USER_READ\"]")
        Set<String> privilegeNames
) {}
