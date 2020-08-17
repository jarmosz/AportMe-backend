package com.aportme.backend.controller;

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

    @PostMapping
    @ApiOperation(value = "Upload pictures")
    public ResponseEntity<Object> upload(@RequestParam Long petId, @RequestBody List<String> base64Pictures) {
        return pictureService.upload(petId, base64Pictures);
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "Set new profile picture")
    public ResponseEntity<Object> setProfilePicture(@PathVariable Long id) {
        return pictureService.setProfilePicture(id);
    }

    @DeleteMapping
    @ApiOperation(value = "Delete pictures")
    public void delete(@RequestParam List<Long> ids) {
        pictureService.delete(ids);
    }
}
