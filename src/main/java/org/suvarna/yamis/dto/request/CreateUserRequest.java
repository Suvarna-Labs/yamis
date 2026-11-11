package org.suvarna.yamis.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "Username is required")
        @Size(max = 150, message = "Username must not exceed 150 characters")
        String username,

        @Size(max = 255, message = "Display name must not exceed 255 characters")
        String displayName,

        @Email(message = "Email must be valid")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        String email,

        Boolean isEnabled,
        Boolean isActive
) {}

