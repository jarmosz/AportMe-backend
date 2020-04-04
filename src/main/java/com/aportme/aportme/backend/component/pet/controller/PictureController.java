package com.aportme.aportme.backend.component.pet.controller;

import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.aportme.aportme.backend.component.pet.dto.pictures.AddPetPictureDTO;
import com.aportme.aportme.backend.component.pet.dto.pictures.PetPictureDTO;
import com.aportme.aportme.backend.component.pet.service.PictureService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/pets/pictures")
public class PictureController {

    private final PictureService pictureService;

    @ApiOperation(value = "Add new picture for pet", response = PetPictureDTO.class)
    @PostMapping
    public DTOEntity add(@RequestParam Long petId, @RequestBody AddPetPictureDTO pictureDTO) throws Exception {
        return pictureService.add(petId, pictureDTO);
    }

    @ApiOperation(value = "Delete picture")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        pictureService.delete(id);
    }
}
