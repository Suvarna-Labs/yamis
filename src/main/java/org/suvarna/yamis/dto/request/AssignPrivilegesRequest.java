package org.suvarna.yamis.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record AssignPrivilegesRequest(
        @NotEmpty(message = "At least one privilege name must be provided")
        Set<String> privilegeNames
) {}

