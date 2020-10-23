package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.pet.AddPetDTO;
import com.aportme.backend.entity.dto.pet.PetBaseDTO;
import com.aportme.backend.entity.dto.pet.PetDTO;
import com.aportme.backend.entity.dto.pet.PetFilters;
import com.aportme.backend.entity.dto.pet.UpdatePetDTO;
import com.aportme.backend.service.PetService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    @GetMapping
    @ApiOperation(value = "Find all pets", response = PetDTO.class)
    public Page<PetDTO> getAll(
            @SortDefault(sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String searchQuery,
            PetFilters filters) {
        return petService.getPets(pageable, searchQuery, filters);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find pet by id", response = PetDTO.class)
    public PetDTO getById(@PathVariable Long id) {
        return petService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@accessService.isFoundation() && @accessService.isFoundationPet(#id)")
    @ApiOperation(value = "Update pet", response = PetDTO.class)
    public PetDTO update(@PathVariable Long id, @RequestBody PetBaseDTO petDTO) {
        return petService.update(id, petDTO);
    }

    @PostMapping
    @PreAuthorize("@accessService.isFoundation()")
    @ApiOperation(value = "Create pet")
    public ResponseEntity<Object> create(@RequestParam Long foundationId, @RequestBody AddPetDTO petDTO) {
        return petService.create(foundationId, petDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessService.isFoundation() && @accessService.isFoundationPet(#id)")
    @ApiOperation(value = "Delete pet")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return petService.delete(id);
    }
}
