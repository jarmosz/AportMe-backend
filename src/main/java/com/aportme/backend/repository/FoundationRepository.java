package com.aportme.backend.repository;

import com.aportme.backend.entity.Foundation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoundationRepository extends JpaRepository<Foundation, Long> {

    @Query("SELECT f FROM Foundation f INNER JOIN users u ON u.id = f.id WHERE u.email = ?1")
    Optional<Foundation> findByEmail(String email);

    Page<Foundation> findAllByAddress_SearchableCityContains(Pageable pageable, String city);
}
