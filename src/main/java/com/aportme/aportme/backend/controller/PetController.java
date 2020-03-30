package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.PetDTO;
import com.aportme.aportme.backend.service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    @GetMapping
    public List<DTOEntity> getAllPets() {
        return petService.getAll();
    }

    @GetMapping("/{id}")
    public DTOEntity getPetById(@PathVariable Long id) {
        return petService.getById(id);
    }

    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable Long id, @RequestBody PetDTO petDTO) throws Exception {
        return petService.update(id, petDTO);
    }

    @PostMapping
    public DTOEntity create(@RequestParam Long foundationId, @RequestBody PetDTO petDTO) throws Exception {
        return petService.create(foundationId, petDTO);
    }
}
