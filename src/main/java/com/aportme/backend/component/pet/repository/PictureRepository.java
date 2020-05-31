package com.aportme.backend.component.pet.repository;

import com.aportme.backend.component.pet.entity.Pet;
import com.aportme.backend.component.pet.entity.PetPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<PetPicture, Long> {
    List<PetPicture> findAllByPet(Pet pet);
}
