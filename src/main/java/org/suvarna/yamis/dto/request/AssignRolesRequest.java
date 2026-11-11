package org.suvarna.yamis.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

@Schema(description = "Request body for assigning roles")
public record AssignRolesRequest(
        @Schema(description = "Set of role names to assign", example = "[\"ROLE_ADMIN\", \"ROLE_MANAGER\"]", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "At least one role name must be provided")
        Set<String> roleNames
) {}
