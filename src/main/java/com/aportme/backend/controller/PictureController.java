package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.picture.PetPictureDTO;
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

    @PostMapping
    @PreAuthorize("@accessService.isFoundation() || @accessService.isFoundationPet(#id)")
    @ApiOperation(value = "Upload pictures for new pet")
    public ResponseEntity<Object> upload(@RequestParam Long petId, @RequestBody List<String> base64Pictures) {
        return pictureService.upload(petId, base64Pictures);
    }

    @PostMapping("/new/{id}")
    @PreAuthorize("@accessService.isFoundation() || @accessService.isFoundationPet(#id)")
    @ApiOperation(value = "Upload new picture for existing pet")
    public PetPictureDTO addNewPicture(@PathVariable Long id, @RequestBody String base64Picture) {
        return pictureService.createPicture(id, base64Picture);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessService.isFoundation() || @accessService.isFoundationPet(#id)")
    public void deletePictureById(@PathVariable Long id) {
        pictureService.deletePictureById(id);
    }

    @PatchMapping("/profile/{id}")
    @PreAuthorize("@accessService.isFoundation() || @accessService.isFoundationPet(#id)")
    @ApiOperation(value = "Set new profile picture")
    public ResponseEntity<Object> setProfilePicture(@PathVariable Long id) {
        return pictureService.setProfilePicture(id);
    }
}
