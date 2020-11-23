package com.aportme.backend.service;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserService userService;
    private final PetService petService;
    private final AuthenticationService authenticationService;

    public ResponseEntity<Object> like(Long petId) {
        Pet pet = petService.findById(petId);
        User user = userService.getLoggedUser();
        user.getLikedPets().add(pet);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> unlike(Long petId) {
        if (arePetLikedByUser(petId)) {
            Pet pet = petService.findById(petId);
            User user = userService.getLoggedUser();
            user.getLikedPets().remove(pet);
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> clearLikes() {
        User user = userService.getLoggedUser();
        user.getLikedPets().clear();
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Boolean arePetLikedByUser(Long petId) {
        Pet pet = petService.findById(petId);
        List<String> userEmails = pet.getUsers()
                .stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
        return userEmails.contains(authenticationService.getAuthentication().getName());
    }
}
