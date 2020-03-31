package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.foundation.FoundationInfoDTO;
import com.aportme.aportme.backend.dto.foundation.FoundationInfoSimpleDTO;
import com.aportme.aportme.backend.service.FoundationInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/foundations")
public class FoundationInfoController {

    private final FoundationInfoService foundationInfoService;

    @GetMapping
    public List<DTOEntity> getAllFoundations() {
        return foundationInfoService.getAll();
    }

    @GetMapping("/{id}")
    public DTOEntity getFoundationById(@PathVariable Long id) {
        return foundationInfoService.getById(id);
    }

    @GetMapping("/search")
    public DTOEntity getFoundationByPetId(@RequestParam Long petId) {
        return foundationInfoService.getByPetId(petId);
    }

    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable Long id, @RequestBody FoundationInfoSimpleDTO foundationInfoSimpleDTO) throws Exception {
        return foundationInfoService.update(id, foundationInfoSimpleDTO);
    }

    @PostMapping
    public DTOEntity create(@RequestParam Long userId, @RequestBody FoundationInfoDTO foundationInfoDTO) throws Exception {
        return foundationInfoService.create(userId, foundationInfoDTO);
    }
}
