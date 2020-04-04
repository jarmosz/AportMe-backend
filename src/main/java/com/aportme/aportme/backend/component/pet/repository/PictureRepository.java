package com.aportme.aportme.backend.component.pet.repository;

import com.aportme.aportme.backend.component.pet.entity.PetPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<PetPicture, Long> {
}
