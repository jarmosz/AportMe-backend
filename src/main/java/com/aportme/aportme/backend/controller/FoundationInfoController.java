package com.aportme.aportme.backend.controller;

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
    public List<FoundationInfo> getAllFoundations() {
        return foundationInfoService.getAll();
    }

    @GetMapping("/{id}")
    public FoundationInfo getFoundationById(@PathVariable Long id) {
        return foundationInfoService.getById(id);
    }

    @GetMapping("/search")
    public FoundationInfo getFoundationByPetId(@RequestParam Long petId) {
        return foundationInfoService.getByPetId(petId);
    }

    @PutMapping("/{id}")
    public FoundationInfo update(@PathVariable Long id, @RequestBody FoundationInfo foundationInfo) {
        return foundationInfoService.update(id, foundationInfo);
    }

    @PostMapping
    public FoundationInfo create(@RequestParam Long userId, @RequestBody FoundationInfo foundationInfo) {
        return foundationInfoService.create(userId, foundationInfo);
    }
}
