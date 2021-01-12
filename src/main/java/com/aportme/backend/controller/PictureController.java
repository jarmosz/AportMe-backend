package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.picture.PetPictureDTO;
import com.aportme.backend.entity.dto.picture.UploadPictureDTO;
import com.aportme.backend.service.PictureService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/pets/pictures")
public class PictureController {

    private final PictureService pictureService;

    @GetMapping("/{id}")
    @PreAuthorize("@accessService.isUser() || @accessService.isFoundationPet(#id)")
    @ApiOperation(value = "Get pictures by pet id")
    public List<PetPictureDTO> getPicturesByPetId(@PathVariable Long id) {
        return pictureService.getById(id);
    }

    @PostMapping("/{petId}")
    @PreAuthorize("@accessService.isFoundation() || @accessService.isFoundationPet(#petId)")
    @ApiOperation(value = "Upload new picture for existing pet")
    public PetPictureDTO addNewPicture(@PathVariable Long petId, @RequestBody UploadPictureDTO pictureDTO) {
        return pictureService.createPicture(petId, pictureDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessService.isFoundation() || @accessService.isFoundationPet(#id)")
    public void deletePictureById(@PathVariable Long id) {
        pictureService.deletePictureById(id);
    }

    @PatchMapping("/profile/{id}")
    @PreAuthorize("@accessService.isFoundation() || @accessService.isFoundationPet(#id)")
    @ApiOperation(value = "Set new profile picture")
    public PetPictureDTO setProfilePicture(@PathVariable Long id) {
        return pictureService.setProfilePicture(id);
    }
}
