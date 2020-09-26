package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.picture.PetPictureDTO;
import com.aportme.backend.service.PictureService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/pets/pictures")
public class PictureController {

    private final PictureService pictureService;

    @GetMapping("/{id}")
    public List<PetPictureDTO> getPicturesByPetId(@PathVariable Long id) {
        return pictureService.getById(id);
    }

    @PostMapping
    @ApiOperation(value = "Upload pictures for new pet")
    public ResponseEntity<Object> upload(@RequestParam Long petId, @RequestBody List<String> base64Pictures) {
        return pictureService.upload(petId, base64Pictures);
    }

    @PostMapping("/new/{id}")
    @ApiOperation(value = "Upload new picture for existing pet")
    public PetPictureDTO addNewPicture(@PathVariable Long id, @RequestBody String base64Picture) {
        return pictureService.createPicture(id, base64Picture);
    }

    @DeleteMapping("/{id}")
    public void deletePictureById(@PathVariable Long id) {
        pictureService.deletePictureById(id);
    }

    @PatchMapping("/profile/{id}")
    @ApiOperation(value = "Set new profile picture")
    public ResponseEntity<Object> setProfilePicture(@PathVariable Long id) {
        return pictureService.setProfilePicture(id);
    }
}
