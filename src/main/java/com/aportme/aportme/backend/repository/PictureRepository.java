package com.aportme.aportme.backend.repository;

import com.aportme.aportme.backend.entity.pet.PetPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<PetPicture, Long> {
}
