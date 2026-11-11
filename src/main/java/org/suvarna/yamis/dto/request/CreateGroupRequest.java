package org.suvarna.yamis.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateGroupRequest(
        @NotBlank(message = "Group name is required")
        @Size(max = 150, message = "Group name must not exceed 150 characters")
        String groupName,

        Boolean isSuperAdmin
) {}

