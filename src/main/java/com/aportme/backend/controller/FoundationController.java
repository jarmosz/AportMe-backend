package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.foundation.AddFoundationDTO;
import com.aportme.backend.entity.dto.foundation.FoundationDTO;
import com.aportme.backend.entity.dto.foundation.LoggedFundationDataDTO;
import com.aportme.backend.entity.dto.foundation.UpdateFoundationDTO;
import com.aportme.backend.service.FoundationService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
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
            @RequestParam(required = false) String searchCityQuery,
            @SortDefault(value = "name", direction = Sort.Direction.ASC) Pageable pageable
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
    public LoggedFundationDataDTO getMyData() {
        return foundationService.getMyData();
    }

    @PutMapping
    @PreAuthorize("@accessService.isFoundation() || @accessService.isAdmin()")
    @ApiOperation(value = "Update foundation")
    public ResponseEntity<Object> updateFoundation(@RequestBody UpdateFoundationDTO updateFoundationDTO) {
        return foundationService.update(updateFoundationDTO);
    }

    @PostMapping
    @PreAuthorize("@accessService.isAdmin()")
    @ApiOperation(value = "Create foundation")
    public ResponseEntity<Object> createFoundation(@RequestParam Long userId, @RequestBody AddFoundationDTO addFoundationDTO) {
        foundationService.create(userId, addFoundationDTO);
        return ResponseEntity.ok().build();
    }
}
