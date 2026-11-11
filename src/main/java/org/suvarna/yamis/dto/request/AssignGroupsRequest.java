package org.suvarna.yamis.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

public record AssignGroupsRequest(
        @NotEmpty(message = "At least one group ID must be provided")
        Set<UUID> groupIds
) {}

