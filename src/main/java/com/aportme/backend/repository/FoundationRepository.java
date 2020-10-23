package com.aportme.backend.repository;

import com.aportme.backend.entity.Foundation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoundationRepository extends JpaRepository<Foundation, Long> {

    @Query("SELECT f FROM Foundation f INNER JOIN Pet p ON p.foundation.id = f.id WHERE p.id=?1")
    Optional<Foundation> findByPetId(Long id);
}
