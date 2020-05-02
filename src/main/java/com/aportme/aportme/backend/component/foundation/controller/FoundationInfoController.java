package com.aportme.aportme.backend.component.foundation.controller;

import com.aportme.aportme.backend.component.foundation.service.FoundationInfoService;
import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.aportme.aportme.backend.component.foundation.dto.AddFoundationDTO;
import com.aportme.aportme.backend.component.foundation.dto.FoundationInfoDTO;
import com.aportme.aportme.backend.component.foundation.dto.UpdateFoundationDTO;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/foundations")
public class FoundationInfoController {

    private final FoundationInfoService foundationInfoService;

    @ApiOperation(value = "Find all foundations", response = FoundationInfoDTO.class)
    @GetMapping
    public List<DTOEntity> getAll() {
        return foundationInfoService.getAll();
    }

    @ApiOperation(value = "Find foundation by id", response = FoundationInfoDTO.class)
    @GetMapping("/{id}")
    public DTOEntity getById(@PathVariable Long id) {
        return foundationInfoService.getById(id);
    }

    @ApiOperation(value = "Search  foundation by pet id", response = FoundationInfoDTO.class)
    @GetMapping("/search")
    public DTOEntity getByPetId(@RequestParam Long petId) {
        return foundationInfoService.getByPetId(petId);
    }

    @ApiOperation(value = "Update foundation", response = FoundationInfoDTO.class)
    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable Long id, @RequestBody UpdateFoundationDTO updateFoundationDTO) throws Exception {
        return foundationInfoService.update(id, updateFoundationDTO);
    }

    @ApiOperation(value = "Create foundation", response = FoundationInfoDTO.class)
    @PostMapping
    public DTOEntity create(@RequestParam Long userId, @RequestBody AddFoundationDTO addFoundationDTO, @RequestParam MultipartFile foundationLogo) throws Exception {
        return foundationInfoService.create(userId, addFoundationDTO, foundationLogo);
    }
}
