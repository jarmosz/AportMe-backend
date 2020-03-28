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
        return foundationInfoService.getFoundationById(id);
    }

    @GetMapping("/search")
    public FoundationInfo getFoundationByPetId(@RequestParam Long petId) {
        return foundationInfoService.getFoundationByPetId(petId);
    }
}
