package org.suvarna.yamis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import java.util.UUID;

@Schema(description = "User details response")
public record UserResponse(
        @Schema(description = "Public unique identifier (UUID)", example = "4a3a76cc-e0dd-4af2-b339-3803409f114d")
        UUID uid,

        @Schema(description = "Unique username", example = "yamisadmin")
        String username,

        @Schema(description = "User's display name", example = "Yamis Superuser")
        String displayName,

        @Schema(description = "User's email", example = "superuser@yamis.local")
        String email,

        @Schema(description = "Indicates if the user account is enabled", example = "true")
        boolean isEnabled,

        @Schema(description = "Indicates if the user is currently active", example = "true")
        boolean isActive,

        @Schema(description = "Set of assigned group names", example = "[\"YAMIS_SUPER_ADMIN\"]")
        Set<String> groupNames
) {}
