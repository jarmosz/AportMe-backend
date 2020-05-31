package com.aportme.backend.component.pet.controller;

import com.aportme.backend.utils.dto.DTOEntity;
import com.aportme.backend.component.pet.dto.AddPetDTO;
import com.aportme.backend.component.pet.dto.PetDTO;
import com.aportme.backend.component.pet.dto.UpdatePetDTO;
import com.aportme.backend.component.pet.service.PetService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    @ApiOperation(value = "Find all pets", response = PetDTO.class)
    @GetMapping
    public Page<PetDTO> getAll(@SortDefault(sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return petService.getAll(pageable);
    }

    @ApiOperation(value = "Find pet by id", response = PetDTO.class)
    @GetMapping("/{id}")
    public DTOEntity getById(@PathVariable Long id) {
        return petService.getById(id);
    }

    @ApiOperation(value = "Update pet", response = PetDTO.class)
    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable Long id, @RequestBody UpdatePetDTO petDTO) throws Exception {
        return petService.update(id, petDTO);
    }

    @ApiOperation(value = "Create pet", response = PetDTO.class)
    @PostMapping
    public DTOEntity create(@RequestParam Long foundationId, @RequestBody AddPetDTO petDTO) throws Exception {
        return petService.create(foundationId, petDTO);
    }

    @ApiOperation(value = "Delete pet")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        petService.delete(id);
    }
}
