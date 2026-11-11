package org.suvarna.yamis.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record CreateRoleRequest(
        @NotBlank(message = "Role name is required")
        @Size(max = 150, message = "Role name must not exceed 150 characters")
        String roleName,

        Set<String> privilegeNames
) {}

