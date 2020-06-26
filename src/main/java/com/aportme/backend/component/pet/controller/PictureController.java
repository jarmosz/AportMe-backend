package com.aportme.backend.component.pet.controller;

import com.aportme.backend.component.pet.dto.pictures.PetPictureDTO;
import com.aportme.backend.component.pet.service.PictureService;
import com.aportme.backend.utils.dto.DTOEntity;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/pets/pictures")
public class PictureController {

    private final PictureService pictureService;

    @ApiOperation(value = "Upload pictures", response = PetPictureDTO.class)
    @PostMapping
    public List<DTOEntity> upload(@RequestParam Long petId, @RequestBody List<String> base64Pictures) {
        return pictureService.upload(petId, base64Pictures);
    }

    @PostMapping("/{id}")
    public DTOEntity setProfilePicture(@PathVariable Long id) throws Exception {
        return pictureService.setProfilePicture(id);
    }

    @ApiOperation(value = "Delete pictures")
    @DeleteMapping
    public void delete(@RequestParam List<Long> ids) {
        pictureService.delete(ids);
    }
}
