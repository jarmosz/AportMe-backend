package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.entity.foundation.FoundationInfo;
import com.aportme.aportme.backend.service.FoundationInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/foundation")
public class FoundationInfoController {

    private final FoundationInfoService foundationInfoService;

    @GetMapping("/getAll")
    public List<FoundationInfo> getAllFoundations() {
        return foundationInfoService.getAll();
    }

    @GetMapping("/getById/{id}")
    public FoundationInfo getFoundationById(@PathVariable Long id) {
        return foundationInfoService.getFoundationById(id);
    }

    @GetMapping("/getByPetId/{id}")
    public FoundationInfo getFoundationByPetId(@PathVariable Long id) {
        return foundationInfoService.getFoundationById(id);
    }
}
