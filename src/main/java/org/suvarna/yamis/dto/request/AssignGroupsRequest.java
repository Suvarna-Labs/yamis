package org.suvarna.yamis.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Request body for assigning groups")
public record AssignGroupsRequest(
        @Schema(description = "Set of group UUIDs to assign", example = "[\"2ff36072-8f44-411f-9c65-f93b918d5c4c\"]", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "At least one group ID must be provided")
        Set<UUID> groupIds
) {}
