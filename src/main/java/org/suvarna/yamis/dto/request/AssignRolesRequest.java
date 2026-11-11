package org.suvarna.yamis.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record AssignRolesRequest(
        @NotEmpty(message = "At least one role name must be provided")
        Set<String> roleNames
) {}

