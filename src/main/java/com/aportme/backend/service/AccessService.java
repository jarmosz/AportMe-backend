package com.aportme.backend.service;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.enums.Role;
import com.aportme.backend.repository.PetRepository;
import com.aportme.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AccessService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public Boolean isAdmin() {
        return getAuthentication().getAuthorities().contains(Role.ADMIN);
    }

    public Boolean isFoundation() {
        return getAuthentication().getAuthorities().contains(Role.FOUNDATION);
    }

    public Boolean isUser() {
        return getAuthentication().getAuthorities().contains(Role.USER);
    }

    public Boolean isFoundationPet(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        String foundationEmail = pet.getFoundation().getUser().getEmail();
        return getAuthentication().getName().equals(foundationEmail);
    }

    public Boolean isMyData(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return getAuthentication().getName().equals(user.getEmail());
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
