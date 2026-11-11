package org.suvarna.yamis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.suvarna.yamis.dto.response.PrivilegeResponse;
import org.suvarna.yamis.service.PrivilegeService;

@RestController
@RequestMapping("/api/v1/privileges")
@Tag(name = "Privilege Management", description = "APIs for inspecting granular system privileges")
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @GetMapping
    @Operation(summary = "List all privileges", description = "Returns a paginated list of all granular privileges.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved privileges page")
    })
    public ResponseEntity<Page<PrivilegeResponse>> getAllPrivileges(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(privilegeService.getAllPrivileges(pageable));
    }

    @GetMapping("/{privilegeName}")
    @Operation(summary = "Get privilege by name", description = "Retrieves details for a specific privilege by name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Privilege found and returned"),
            @ApiResponse(responseCode = "404", description = "Privilege not found")
    })
    public ResponseEntity<PrivilegeResponse> getPrivilegeByName(
            @Parameter(description = "Unique name of the privilege (e.g. USER_CREATE)", required = true)
            @PathVariable String privilegeName) {
        return ResponseEntity.ok(privilegeService.getPrivilegeByName(privilegeName));
    }
}
