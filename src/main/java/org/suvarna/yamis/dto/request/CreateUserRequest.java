package org.suvarna.yamis.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for creating a new user")
public record CreateUserRequest(
        @Schema(description = "Unique username", example = "john_doe", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Username is required")
        @Size(max = 150, message = "Username must not exceed 150 characters")
        String username,

        @Schema(description = "User's display name", example = "John Doe")
        @Size(max = 255, message = "Display name must not exceed 255 characters")
        String displayName,

        @Schema(description = "User's email address", example = "john.doe@example.com")
        @Email(message = "Email must be valid")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        String email,

        @Schema(description = "Whether the user account is enabled", example = "true", defaultValue = "true")
        Boolean isEnabled,

        @Schema(description = "Whether the user is active", example = "true", defaultValue = "true")
        Boolean isActive
) {}
