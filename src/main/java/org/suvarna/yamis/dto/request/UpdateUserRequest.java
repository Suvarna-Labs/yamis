package org.suvarna.yamis.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(max = 255, message = "Display name must not exceed 255 characters")
        String displayName,

        @Email(message = "Email must be valid")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        String email,

        Boolean isEnabled,
        Boolean isActive
) {}

