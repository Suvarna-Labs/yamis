package org.suvarna.yamis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Privilege details response")
public record PrivilegeResponse(
        @Schema(description = "Unique privilege name", example = "USER_CREATE")
        String privilegeName,

        @Schema(description = "Description of permissions granted by this privilege", example = "Create new users")
        String description
) {}
