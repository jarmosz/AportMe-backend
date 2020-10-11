package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.foundation.AddFoundationDTO;
import com.aportme.backend.entity.dto.foundation.FoundationDTO;
import com.aportme.backend.entity.dto.foundation.UpdateFoundationDTO;
import com.aportme.backend.service.FoundationService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/foundations")
public class FoundationController {

    private final FoundationService foundationService;

    @GetMapping
    @ApiOperation(value = "Find all foundations", response = FoundationDTO.class)
    public Page<FoundationDTO> getAllFoundations(@SortDefault(value = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return foundationService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find foundation by id", response = FoundationDTO.class)
    public FoundationDTO getFoundationById(@PathVariable Long id) {
        return foundationService.getById(id);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search  foundation by pet id", response = FoundationDTO.class)
    public FoundationDTO getFoundationByPetId(@RequestParam Long petId) {
        return foundationService.getByPetId(petId);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update foundation")
    public ResponseEntity<Object> updateFoundation(@PathVariable Long id, @RequestBody UpdateFoundationDTO updateFoundationDTO) {
        return foundationService.update(id, updateFoundationDTO);
    }

    @PostMapping
    @ApiOperation(value = "Create foundation")
    public ResponseEntity<Object> createFoundation(@RequestParam Long userId, @RequestBody AddFoundationDTO addFoundationDTO) {
        return foundationService.create(userId, addFoundationDTO);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "Upload foundation logo")
    public ResponseEntity<Object> uploadFoundationLogo(@RequestParam Long id, @RequestBody String base64Logo) {
        return foundationService.uploadFoundationLogo(id, base64Logo);
    }
}
