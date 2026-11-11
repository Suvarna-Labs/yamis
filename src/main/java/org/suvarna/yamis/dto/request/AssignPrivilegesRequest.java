package org.suvarna.yamis.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

@Schema(description = "Request body for assigning privileges")
public record AssignPrivilegesRequest(
        @Schema(description = "Set of privilege names to assign", example = "[\"USER_CREATE\", \"USER_READ\"]", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "At least one privilege name must be provided")
        Set<String> privilegeNames
) {}
