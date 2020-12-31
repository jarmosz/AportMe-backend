package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.foundation.AddFoundationDTO;
import com.aportme.backend.facade.AdminFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@PreAuthorize("@accessService.isAdmin()")
public class AdminController {

    private final AdminFacade adminFacade;

    @PostMapping("/create-foundation")
    @ApiOperation(value = "Create foundation")
    public ResponseEntity<Object> createFoundation(@RequestBody AddFoundationDTO addFoundationDTO) {
        adminFacade.createFoundation(addFoundationDTO);
        return ResponseEntity.ok().build();
    }
}
