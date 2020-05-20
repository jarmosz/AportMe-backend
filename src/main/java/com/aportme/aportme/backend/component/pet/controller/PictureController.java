package com.aportme.aportme.backend.component.pet.controller;

import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.aportme.aportme.backend.component.pet.dto.pictures.PetPictureDTO;
import com.aportme.aportme.backend.component.pet.service.PictureService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/pets/pictures")
public class PictureController {

    private final PictureService pictureService;

    @ApiOperation(value = "Upload pictures", response = PetPictureDTO.class)
    @PostMapping
    public List<DTOEntity> upload(@RequestParam Long petId, @RequestParam("pictures") MultipartFile[] pictures) {
        return pictureService.upload(petId, pictures);
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
