package org.suvarna.yamis.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for updating an existing user")
public record UpdateUserRequest(
        @Schema(description = "User's display name", example = "Johnathan Doe")
        @Size(max = 255, message = "Display name must not exceed 255 characters")
        String displayName,

        @Schema(description = "User's email address", example = "j.doe@example.com")
        @Email(message = "Email must be valid")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        String email,

        @Schema(description = "Whether the user account is enabled", example = "true")
        Boolean isEnabled,

        @Schema(description = "Whether the user is active", example = "true")
        Boolean isActive
) {}
