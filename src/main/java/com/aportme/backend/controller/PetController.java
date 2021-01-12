package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.pet.*;
import com.aportme.backend.service.PetService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    @GetMapping
    @ApiOperation(value = "Find all pets for mobile", response = SimplePetDTO.class)
    public Page<SimplePetDTO> getAll(Pageable pageable, PetFilters filters) {
        return petService.getPets(pageable, filters);
    }

    @GetMapping("/my")
    @PreAuthorize("@accessService.isFoundation()")
    @ApiOperation(value = "Find all logged foundation Pets", response = PetDTO.class)
    public Page<PetDTO> getAll(Pageable pageable, @RequestParam(defaultValue = "") String query) {
        return petService.getFoundationPets(pageable, query);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find pet by id", response = PetDTO.class)
    public PetDTO getById(@PathVariable Long id) {
        return petService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@accessService.isFoundation() && @accessService.isFoundationPet(#id)")
    @ApiOperation(value = "Update pet", response = PetDTO.class)
    public PetBaseDTO update(@PathVariable Long id, @RequestBody PetBaseDTO petDTO) {
        return petService.update(id, petDTO);
    }

    @PostMapping
    @PreAuthorize("@accessService.isFoundation()")
    @ApiOperation(value = "Create pet")
    public PetDTO create(@RequestBody AddPetDTO petDTO) {
        return petService.create(petDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessService.isFoundation() && @accessService.isFoundationPet(#id)")
    @ApiOperation(value = "Delete pet")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        petService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
