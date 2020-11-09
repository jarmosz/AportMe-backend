package com.aportme.backend.service;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserService userService;
    private final PetService petService;

    public ResponseEntity<Object> like(Long petId) {
        Pet pet = petService.findById(petId);
        User user = userService.getLoggedUser();
        user.getLikedPets().add(pet);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> unlike(Long petId) {
        Pet pet = petService.findById(petId);
        User user = userService.getLoggedUser();
        user.getLikedPets().remove(pet);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> clearLikes() {
        User user = userService.getLoggedUser();
        user.getLikedPets().clear();
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
