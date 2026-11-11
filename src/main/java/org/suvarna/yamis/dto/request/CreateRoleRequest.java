package org.suvarna.yamis.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Schema(description = "Request body for creating a new role")
public record CreateRoleRequest(
        @Schema(description = "Unique role name", example = "ROLE_MANAGER", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Role name is required")
        @Size(max = 150, message = "Role name must not exceed 150 characters")
        String roleName,

        @Schema(description = "Set of privilege names to associate with this role", example = "[\"USER_READ\", \"GROUP_READ\"]")
        Set<String> privilegeNames
) {}
