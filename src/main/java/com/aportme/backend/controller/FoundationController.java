package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.foundation.FoundationDTO;
import com.aportme.backend.entity.dto.foundation.LoggedFoundationDataDTO;
import com.aportme.backend.entity.dto.foundation.UpdateFoundationDTO;
import com.aportme.backend.service.FoundationService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/foundations")
public class FoundationController {

    private final FoundationService foundationService;

    @GetMapping
    @ApiOperation(value = "Find all foundations", response = FoundationDTO.class)
    public Page<FoundationDTO> getAllFoundations(
            @RequestParam(defaultValue = "") String searchCityQuery,
            Pageable pageable
    ) {
        return foundationService.getAll(searchCityQuery, pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find foundation by id", response = FoundationDTO.class)
    public FoundationDTO getFoundationById(@PathVariable Long id) {
        return foundationService.getById(id);
    }

    @GetMapping("/profile")
    @PreAuthorize("@accessService.isFoundation()")
    @ApiOperation(value = "Find all foundations", response = FoundationDTO.class)
    public LoggedFoundationDataDTO getMyData() {
        return foundationService.getMyData();
    }

    @PutMapping
    @PreAuthorize("@accessService.isFoundation() || @accessService.isAdmin()")
    @ApiOperation(value = "Update foundation")
    public UpdateFoundationDTO updateFoundation(@RequestBody UpdateFoundationDTO updateFoundationDTO) {
        return foundationService.update(updateFoundationDTO);
    }
}
