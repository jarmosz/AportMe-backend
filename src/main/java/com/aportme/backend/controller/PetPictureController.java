package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.picture.ChangeProfilePictureDTO;
import com.aportme.backend.entity.dto.picture.PetPictureDTO;
import com.aportme.backend.entity.dto.picture.UploadPictureDTO;
import com.aportme.backend.facade.PetPictureFacade;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/pets/pictures")
public class PetPictureController {

    private final PetPictureFacade petPictureFacade;

    @GetMapping("/{petId}")
    @PreAuthorize("@accessService.isUser() || @accessService.isFoundationPet(#petId)")
    @ApiOperation(value = "Get pictures by pet id")
    public List<PetPictureDTO> getPicturesByPetId(@PathVariable Long petId) {
        return petPictureFacade.findPetsPictureByPetId(petId);
    }

    @PostMapping("/{petId}")
    @PreAuthorize("@accessService.isFoundation() || @accessService.isFoundationPet(#petId)")
    @ApiOperation(value = "Upload new picture for existing pet")
    public PetPictureDTO uploadPicture(@PathVariable Long petId, @RequestBody UploadPictureDTO dto) {
        return petPictureFacade.addPicture(petId, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessService.isFoundation() || @accessService.isFoundationPet(#id)")
    public void deletePictureById(@PathVariable Long id) {
        petPictureFacade.deletePicture(id);
    }

    @PatchMapping("/change-profile-picture")
    @PreAuthorize("@accessService.isFoundation()")
    @ApiOperation(value = "Set new profile picture")
    public PetPictureDTO setProfilePicture(@RequestBody ChangeProfilePictureDTO dto) {
        return petPictureFacade.changeProfilePicture(dto);
    }
}
