package org.suvarna.yamis.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for updating an existing group")
public record UpdateGroupRequest(
        @Schema(description = "Unique name of the group", example = "LEAD_DEVELOPERS", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Group name is required")
        @Size(max = 150, message = "Group name must not exceed 150 characters")
        String groupName,

        @Schema(description = "Whether the group has super admin privileges", example = "false")
        Boolean isSuperAdmin
) {}
