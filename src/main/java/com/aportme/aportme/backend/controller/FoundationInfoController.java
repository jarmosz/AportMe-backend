package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.foundation.AddFoundationDTO;
import com.aportme.aportme.backend.dto.foundation.UpdateFoundationInfoDTO;
import com.aportme.aportme.backend.entity.foundation.FoundationInfo;
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
    public List<DTOEntity> getAll() {
        return foundationInfoService.getAll();
    }

    @GetMapping("/{id}")
    public DTOEntity getById(@PathVariable Long id) {
        return foundationInfoService.getById(id);
    }

    @GetMapping("/search")
    public DTOEntity getByPetId(@RequestParam Long petId) {
        return foundationInfoService.getByPetId(petId);
    }

    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable Long id, @RequestBody UpdateFoundationInfoDTO updateFoundationInfoDTO) throws Exception {
        return foundationInfoService.update(id, updateFoundationInfoDTO);
    }

    @PostMapping
    public FoundationInfo create(@RequestParam Long userId, @RequestBody AddFoundationDTO addFoundationDTO) throws Exception {
        return foundationInfoService.create(userId, addFoundationDTO);
    }
}
