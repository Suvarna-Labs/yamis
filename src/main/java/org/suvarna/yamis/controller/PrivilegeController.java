package org.suvarna.yamis.controller;

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
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @GetMapping
    public ResponseEntity<Page<PrivilegeResponse>> getAllPrivileges(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(privilegeService.getAllPrivileges(pageable));
    }

    @GetMapping("/{privilegeName}")
    public ResponseEntity<PrivilegeResponse> getPrivilegeByName(@PathVariable String privilegeName) {
        return ResponseEntity.ok(privilegeService.getPrivilegeByName(privilegeName));
    }
}

