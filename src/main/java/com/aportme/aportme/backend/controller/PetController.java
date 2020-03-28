package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.entity.pet.Pet;
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
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }
}
