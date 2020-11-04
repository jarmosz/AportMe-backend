package com.aportme.backend.service;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.enums.Role;
import com.aportme.backend.repository.PetRepository;
import com.aportme.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AccessService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final AuthenticationService authenticationService;

    public Boolean isAdmin() {
        return authenticationService.getAuthorities().contains(Role.ADMIN);
    }

    public Boolean isFoundation() {
        return authenticationService.getAuthorities().contains(Role.FOUNDATION);
    }

    public Boolean isUser() {
        return authenticationService.getAuthorities().contains(Role.USER);
    }

    public Boolean isFoundationPet(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        String foundationEmail = pet.getFoundation().getUser().getEmail();
        return authenticationService.getAuthentication().getName().equals(foundationEmail);
    }
}
